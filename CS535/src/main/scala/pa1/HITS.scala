package pa1
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.apache.log4j.Logger
import org.apache.log4j.Level

object HITS {
  
  def main(args: Array[String]) = {
  
    Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		
    //Start the Spark context
    val conf = new SparkConf()
      .setAppName("HITS")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val search_topic = "B"
    
    //Read some example file to a test RDD
    val all_links = sc.textFile("res_PA1/input/links_sample.txt")
                      .map{x => 
                      val l = x.split(": ")
                      (l(0).toLong, l(1).split(" ").map(_.toString.toLong).toSet)}
    
    val titles = sc.textFile("res_PA1/input/titles_sample.txt").zipWithIndex().mapValues(x => x+1).map(_.swap)
    val topic_titles = titles.filter(_._2 == search_topic)
    val topic_set = topic_titles.keys.collect().toSet
    // root_set: pages that have contains search topic in the title
    val root_set = all_links.filter{case (page, links) => topic_set.contains(page)}
    val out_topic_set = root_set.values.collect().flatten.toSet
 
    val neighboor_set = all_links.filter{case (page, links) => 
      out_topic_set.contains(page) || // pages that are linked by the root_set
      links.exists(l => topic_set.contains(l)) // pages that have links to root_set
      }
    
    val base_set = root_set++neighboor_set

    println("root set")
    root_set.collect().foreach(println)
    println("neighboor set")
    neighboor_set.collect().foreach(println)
    println("base set")
    base_set.collect().foreach(println)

    // base_set [from: in1 in2 ...] inv_base_set [in: from1 from2...]
    val inv_base_set = base_set.flatMapValues(f => f).map(_.swap).groupByKey()
    
    val authority_scores = base_set.map{case(page,links) => (page,1)}
    val hub_scores = base_set.map{case(page,links) => (page,1)}
    
    for(it <- 1 to 1){
				  //hub_scores.lookup(x))
      //val hub_scores = base_set.map{case(page,links) => (page,1)}
    }

      //  val authority_scores2 = inv_base_set.mapValues(x => x.foreach(f => hub_scores.lookup(f)))
 /////////////////////////////////////////////////////////////////
    // VERSIONING to 2 to try tuples (int, int) instead of (int,iterable[int])
    
   
    //authority_scores.map{case(page,score) => base_set
        println("test")

        hub_scores.collect().foreach(println)
    
    
    
    //println(base_set.lookup(2))
    //println(all_links.getClass.toString())

    //Stop the Spark context
    sc.stop
  }
}
