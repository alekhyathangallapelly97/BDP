import org.apache.spark.{SparkConf, SparkContext}
object DFS {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "c:\\winutils")
    val conf = new SparkConf().setAppName("Depthfirst").setMaster("local[*]")
    val sc = new SparkContext(conf)
    type V = Int
    type Graph = Map[V, List[V]]
    val g: Graph = Map(1 -> List(1, 2, 3), 2 -> List(3, 4, 6), 3 -> List(1, 3), 4 -> List(4, 5), 6 -> List(2, 4), 5 -> List(2, 6))
    def DFS(start: V, g: Graph): List[V]={
      var visited=List(start)
      var result=List(start)

      def DFS0(start: V): Unit={
        for(n<-g(start); if !visited.contains(n)){
          visited=n :: visited
          result=n :: result
          DFS0(n)
        }}
      DFS0(start)
      result.reverse
    }
    val result=DFS(1,g)
    println(result.mkString(","))
  }
}
