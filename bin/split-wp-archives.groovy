#!/usr/bin/env groovy
file = args[0] as File
outFolder = args[1] as File
println "Loading $file, spitting out files in $outFolder"

def doc = new XmlSlurper().parse(file).declareNamespace([
        wp: 'http://wordpress.org/export/1.1/',
        excerpt:"http://wordpress.org/export/1.1/excerpt/",
        content:"http://purl.org/rss/1.0/modules/content/"
])
//def doc = new XmlParser().parse(file)
// doc.channel.item.each { println "${it['wp:post_name']} - \"${it.title}\"" }
doc.channel.item.each {
    def name = it.'wp:post_name'
    if (!name) {
        throw new Exception("no name for ${it}")
    }

    def title = it.title
    def type = it.'wp:post_type'
    def status = it.'wp:status'
    def content = it.'content:encoded'
    def excerpt = it.'excerpt:encoded'
    // Mon, 18 Jul 2005 23:53:16 +0000"
    // 'EE, d MM yyyy HH:mm:ss Z'
    def pubDate = Date.parse("EEE, d MMM yyyy HH:mm:ss Z", it.pubDate.toString())

    println "$type : ${name} - ${status} - \"${title}\" - ${pubDate}"
    def folder = new File(outFolder, "${type}/${status}")
    folder.mkdirs()
    def postFile = new File(folder, "${name}.md")
    postFile.withWriter('UTF-8') { out ->
        out.println("---")
        out.println("title: ${title}")
        out.println("type: ${type}")
        out.println("excerpt: ${excerpt}")
        out.println("---")
        out.println(content)
        out.flush()
    }
}
// item
// title
// wp:post_name
// pubDate
// wp:status
