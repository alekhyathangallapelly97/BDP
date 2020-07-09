  import org.apache.spark._

  object WC {
    def main(args: Array[String]) {

      System.setProperty("hadoop.home.dir","C:\\winutils" )
      //val inputFile = args(0)
      //val outputFile = args(1)
      val conf = new SparkConf().setAppName("wordCount").setMaster("local[*]")
      // Create a Scala Spark Context.
      val sc = new SparkContext(conf)
      // Load our input data.
      //val input =  sc.textFile(inputFile)
      val input = sc.textFile("INPUT1.txt")
      //Specifying the output path name
      val output = "output_Wordcount"
      // Split up into words.
      val words = input.flatMap(line => line.split("\\W+"))
      // Transform into word and count.
      val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
      //Sorting the output
      val wrdList=counts.sortBy(outputList=>outputList._1,ascending = true)
      // Save the word count back out to a text file, causing evaluation.
      wrdList.repartition(1).saveAsTextFile(output)
      //Displaying output on the screen
      wrdList.take(10).foreach(outputList=>println(outputList))
      sc.stop()

    }


}
