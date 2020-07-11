import org.apache.log4j.{Level, Logger}
import org.apache.spark._
object MergeSort {
  def main(args: Array[String]): Unit = {

    //Controlling log level
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    //Context about the spark
    val conf = new SparkConf().setAppName("MergeSort").setMaster("local");
    val sc = new SparkContext(conf);
    val arr = Array(5,71,2,667,325,50);
    val brr = sc.parallelize(Array(5,71,2,667,325,50));
    val arraymap = brr.map(x => (x, 1))
    val sort = arraymap.sortByKey();

    sort.keys.collect().foreach(println)

    val n = arr.length;
    val l1 = 0;
    val s = n - 1;
    mergesort(arr, l1,s);

    // Sorting of the Array
    def mergesort(arr: Array[Int], l1: Int, z: Int): Unit = {
      if (l1 < z) { //middle point
        val y = (l1 + z) / 2
        // Sort first and second halves
        mergesort(arr, l1,y)
        mergesort(arr,y +1, z)
        // Merge
        merge(arr, l1, y, z)
      }
    }

    // Merge for the arrays
    def merge(arr: Array[Int], l1: Int, x: Int, v: Int): Unit = {
      //sizes of two subarrays to mergE
      val num1 = x - l1 + 1
      val num2 = v - x

      /*temp arrays */
      val L = new Array[Int](num1)
      val R = new Array[Int](num2)
      //Copy data to temp arrays
      var a = 0
      while (a < num1) {
        L(a) = arr(l1 + a);
        a += 1;
      }
      var b = 0
      while (b < num2) {
        R(b) = arr(x + 1 + b);
        b += 1;
      }

      /* Merge temp arrays */
      // indexes of first and second subarrays
      var l = 0
      var k = 0
      // index of merged subarray array
      var h = l1
      while (l < num1 && k < num2) {
        if (L(l) <= R(k)) {
          arr(k) = L(l)
          l += 1
        }
        else {
          arr(k) = R(k)
          k += 1
        }
        k += 1
      }

      /* Copy remaining elements of L[]*/
      while (l < num1) {
        arr(k) = L(l)
        l += 1
        k += 1
      }

      /* Copy remaining elements of R[]*/
      while (k < num2) {
        arr(k) = R(k)
        k += 1
        k += 1
      }
    }
  }
}