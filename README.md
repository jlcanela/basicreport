# Basic Report

This project is a basic report written in Spark. 
* it read an apache log file
* it generates a report with daily ips and uris distributions (counts of differents ips and uris for a given day)

Example of the report: 
```
{
"date" : "today",
"ip_list": [ 
    { "ip": "10.10.10.1", "count": "122"},
    { "ip": "10.10.10.1", "count": "122"}
], 
"uri_list": [ 
    { "uri": "/admin", "count": "122"},
    { "uri": "/dangerous_uri", "count": "129902"}
]
} 
```

# Run the project 

To run the project:
```
git clone git@github.com:jlcanela/basicreport.git
cd basicreport
curl -o access.log.gz -L https://github.com/jlcanela/spark-hands-on/raw/master/almhuette-raith.log/access.log.gz
./mill report.standalone.run import access.log.gz data/accesslogs
./mill report.standalone.run report data/accesslogs data/report
```

# Run using spark submit 

```
./mill report.assembly
spark-submit --class ReportCli out/report/assembly/dest/out.jar import access.log.gz data/accesslogs 
spark-submit --class ReportCli out/report/assembly/dest/out.jar report data/accesslogs data/report
```

# Tools used 

mill: https://com-lihaoyi.github.io/mill/mill/Intro_to_Mill.html

