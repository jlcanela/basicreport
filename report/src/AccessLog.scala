
case class AccessLog(ip: String, ident: String, user: String, datetime: String, request: String, status: String, size: String, referer: String, userAgent: String, unk: String)

object AccessLog {
    val R = """^(?<ip>[0-9.]+) (?<identd>[^ ]) (?<user>[^ ]) \[(?<datetime>[^\]]+)\] \"(?<request>[^\"]*)\" (?<status>[^ ]*) (?<size>[^ ]*) \"(?<referer>[^\"]*)\" \"(?<useragent>[^\"]*)\" \"(?<unk>[^\"]*)\"""".r

    def fromString(s: String) = s match { 
        case R(ip: String, ident: String, user: String, datetime: String, request: String, status: String, size: String, referer: String, userAgent: String, unk: String) => Some(AccessLog(ip, ident, user, datetime, request, status, size, referer, userAgent, unk))
        case _ => None 
    }
}
