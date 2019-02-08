package pa1
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.rdd.RDD
object HITS2 {
  
  def main(args: Array[String]) = {
  
    //just to avoid annoying log messages
    Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		
    //Start the Spark context
    val conf = new SparkConf()
      .setAppName("HITS")
      .setMaster("local")
    val sc = new SparkContext(conf)
    
    // search entry passed as firs argument
    val search_topic = args(2)
    
    // where all th input files should be located
    val input_dir = "res_PA1/input/"
    
    //Read some example file to a test RDD
    val all_links = sc.textFile(input_dir+args(0))
                      .map{x => 
                      val l = x.split(": ") //split the line by from | to1 to2 to3
                      (l(0).toLong, l(1).split(" ").map(_.toString.toLong).toSet)}//use <from> as de key and the tokenized <to1 to2...> as de value
    
    //read the titles file and map each title with their index so it will be like <N,titleN>
    val titles = sc.textFile(input_dir+args(1)).zipWithIndex().mapValues(x => x+1).map(_.swap)
    // keep only the titles which contain the search entry.
    val topic_titles = titles.filter(_._2 contains search_topic)
    // create a Set to be used in th next line
    val topic_set = topic_titles.keys.collect().toSet
    // root_set: pages that contains search topic in the title
    val root_set = all_links.filter{case (page, links) => topic_set.contains(page)}
    // all pages being linked by the root set
    val out_topic_set = root_set.values.collect().flatten.toSet
    // pages related to the search entry by a link to/from it
    val neighboor_set = all_links.filter{case (page, links) => 
      out_topic_set.contains(page) || // pages that are linked by the root_set
      links.exists(l => topic_set.contains(l)) // pages that have links to root_set
      }
    // all pages related to the search entry: a) the entry is in the title, b) one of "a" has a link to it, c) one of "a" is being link from it
    // in a map of <from, to>
    val base_set = (root_set++neighboor_set).flatMapValues(f=>f)

    //debugging stuff
    println("root set")
    root_set.collect().foreach(println)
    println("neighboor set")
    neighboor_set.collect().foreach(println)
    println("base set")
    base_set.collect().foreach(println)

    //  same as base_set but inverted <to, from>
    val inv_base_set = base_set.map(_.swap)
    // list of all pages involved in the search
    val pages = base_set.keys.distinct.union(base_set.values.distinct).distinct()
    // <page,score> initialized with score=1
    var authority_scores = pages.map(t => (t, 1.0))//base_set_compact.map{case(page,links) => (page,0.1)}
    var hub_scores = pages.map(t => (t, 1.0))//base_set_compact.map{case(page,links) => (page,0.1)}

    // loop to converge the scores
    for(it <- 1 to 5){
      println("Step "+it)
      // new score without normalization
      var authority_scores_aux : RDD[(Long, Double)] = base_set.join(hub_scores).map{case(from,(to,hub)) => (to,hub)}.reduceByKey((a,b) => a+b)
      // complete the RDD in case any link is missing because doesn't have incoming links. It's necessary?
      authority_scores_aux = authority_scores_aux.union(authority_scores.keys.subtract(authority_scores_aux.keys).map(f=>(f,0.0)))
      // new score without normalization
      var hub_scores_aux : RDD[(Long, Double)] = inv_base_set.join(authority_scores).map{case(to,(from,auth)) => (from,auth)}.reduceByKey((a,b) => a+b)
      // complete the RDD in case any link is missing because doesn't have outgoing links. It's necessary?
      hub_scores_aux = hub_scores_aux.union(hub_scores.keys.subtract(hub_scores_aux.keys).map(f=>(f,0.0)))
      // sum of scores without normalization
			val hub_normalization = hub_scores_aux.values.sum()
			val authority_normalization = authority_scores_aux.values.sum()
			// normalizing the scores
			hub_scores = hub_scores_aux.mapValues(h => h/hub_normalization)
			authority_scores = authority_scores_aux.mapValues(a => a/authority_normalization)
    }
    // match the scores with the titles and sort the in descending order    
    val rankedByHub = hub_scores.join(titles).map{case(index,(score,title)) => (title,score)}.sortBy(_._2, false)
    val rankedByAuthority = authority_scores.join(titles).map{case(index,(score,title)) => (title,score)}.sortBy(_._2, false)
   
    println("rankedByHub")
    rankedByHub.collect().foreach(println)
    
    println("rankedByAuthority")
    rankedByAuthority.collect().foreach(println)

    //Stop the Spark context
    sc.stop
  }
}
