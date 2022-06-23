// import org.apache.spark.sql.functions._
// import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SparkSession
// import org.apache.log4j.Logger;

object Report {

    def report(spark: SparkSession, in: String, out: String) = {
        println(s"apache report file to read: ’$in’")
        println(s"json report file to write: ’$out’")
    }
}