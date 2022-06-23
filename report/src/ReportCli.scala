

object ReportCli {

    def main(args: Array[String]) = args match {
        case Array("import", apacheLogFilePath, parquetAccessLogPath) => SparkContext.runWithSpark(AccessLogsImporter(_).importLogs(apacheLogFilePath, parquetAccessLogPath))
        case Array("report", parquetAccessLogPath, jsonReportPath) => SparkContext.runWithSpark(Report(_).report(parquetAccessLogPath, jsonReportPath))
        case _ => 
            println(s"command '${args.mkString(" ")}' not recognized")
            println("usage:\n\r\t./mill report.run import <path_to_apache_logs> <path_to_parquet_import>\n\n\t./mill report.run report <path_to_parquet_import> <path_to_json_report>")
    }
    
}