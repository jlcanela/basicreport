import org.apache.spark.sql.SparkSession

object SparkContext {

    private def createSession = {
        val builder = SparkSession.builder.appName("Spark Report")
        //.config("spark.eventLog.enabled", true)
        //.config("spark.eventLog.dir", "./spark-logs")
        builder.getOrCreate()
    }

    def runWithSpark(f: SparkSession => Unit): Unit = {
        val spark = createSession
        f(spark)
        spark.close
    }

}