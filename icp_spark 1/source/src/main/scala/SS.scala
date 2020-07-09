  import org.apache.spark._

  object SS {
    def main(args: Array[String]) {
      System.setProperty("hadoop.home.dir", "C:\\winutils")
      val conf = new SparkConf().setAppName("SECONDARYSORTING").setMaster("local[*]")
      val sc = new SparkContext(conf)

      val personRDD = sc.textFile("INPUT2.txt")
      val pairsRDD = personRDD.map(_.split(",")).map { m => ((m(0), m(1)), m(2)) }
      println("PAIRS")
      pairsRDD.foreach {
        println
      }
      val numReducers = 1;

      val list = pairsRDD.groupByKey(numReducers).mapValues(iter => iter.toList.sortBy(k => k))
      println("LIST")
      list.saveAsTextFile("output_Secondary_Sort");
    }


}
