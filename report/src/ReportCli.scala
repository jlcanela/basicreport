

object ReportCli {

    def main(args: Array[String]) = args match {
        case Array("report", in, out) => Report.report(in, out)
        case _ => 
            println(s"command '${args.mkString(" ")}' not recognized")
            println("usage:\n\r\t./mill report.run report <path_to_apache_logs> <path_to_json_report>")
    }
    
}