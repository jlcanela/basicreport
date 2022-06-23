import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AccessLogsImporter {

  val REQ_EX = "([^ ]+)[ ]+([^ ]+)[ ]+([^ ]+)".r

  def addRequestColumns(spark: SparkSession, ds: DataFrame) = ds
    .withColumn(
      "method",
      regexp_extract(ds("request"), REQ_EX.toString, 1)
    )
    .withColumn(
      "uri",
      regexp_extract(ds("request"), REQ_EX.toString, 2)
    )
    .withColumn(
      "http",
      regexp_extract(ds("request"), REQ_EX.toString, 3)
    )
    .drop("request")

  def importLogs(spark: SparkSession, apacheLogFilePath: String, parquetAccessLogPath: String) = {
    import spark.implicits._

    val logsAsText = spark.read.text(apacheLogFilePath).as[String]
    val logsAsAccessLog = logsAsText.flatMap(AccessLog.fromString _)
    val logsWithDateTime = logsAsAccessLog.withColumn(
      "datetime",
      to_timestamp(logsAsAccessLog("datetime"), "dd/MMM/yyyy:HH:mm:ss X")
    )

    val enrichedAccessLog = addRequestColumns(spark, logsWithDateTime)

    enrichedAccessLog.write
      .option("compression", "gzip")
      .mode("Overwrite")
      .parquet(parquetAccessLogPath)
  }
}
