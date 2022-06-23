import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._

// import org.apache.log4j.Logger;

case class Report(spark: SparkSession) {

  import spark.implicits._

  def report(parquetAccessLogPath: String, jsonReportPath: String) = {
    
    spark.read
      .parquet(parquetAccessLogPath)
      .withColumn("date", col("datetime").cast("date"))
      .createOrReplaceTempView("logs")

    val dates = spark
      .sql("""
        with dates as (
            select date, count(*) as cnt 
            from logs 
            group by date 
            having count(*) > 20000
            order by cnt desc)
        select date from dates             
        """)
      .createTempView("dates")


    def createCountByFieldView(field: String) = spark
      .sql(s"""
        with ${field}s as (
            select date, $field, count(*) as cnt
            from logs
            group by date, $field
        )

        select date, collect_list(struct($field, cnt as count)) as ${field}_list from ${field}s group by date
        """)
      .createTempView(s"${field}s")

    createCountByFieldView("ip")
    createCountByFieldView("uri")

    val report = spark.sql("""
        select dates.date, ips.ip_list, uris.uri_list
        from dates 
        inner join ips on ips.date = dates.date 
        inner join uris on uris.date = dates.date
        """)

    report.coalesce(1).write.mode("Overwrite").json(jsonReportPath)

  }
}
