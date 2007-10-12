<?

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * The urltranslater translates urls from the rss items to actual download urls
 * where ted can download the torrent from
 * 
 * @author Roel
 * @author Joost
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
 
 /**
	 * Translates the url from the specified source to a url where ted can download
	 * the torrent from
	 * @param url URL as listed in the RSS feed
	*/

$url = $_GET["url"];
$torrentUrl	= $url;

if(		strpos($url, "bt-chat.com") !== false ||
		strpos($url, "thepiratebay.org") !== false || 
		strpos($url, "mrtwig.net") !== false ||
		strpos($url, "torrentlocomotive.com") !== false ||
		strpos($url, "sdnett.org") !== false ||
		strpos($url, "torrentbytes.net") !== false ||
		strpos($url, "torrentleech.org") !== false ||
		strpos($url, "digitaldistractions.org") !== false )
{
	// do nothing, url already in right format
}
else if (strpos($url, "btjunkie.org") !== false)
{
	// url in feed: http://btjunkie.org/torrent/this-american-life-106-hdtv-xvid-kyr-avi/2908c5164f4f566eb149c60c83d4896edbf3012943ed%26id%3D
	// has to be http://btjunkie.org/torrent/this-american-life-106-hdtv-xvid-kyr-avi/2908c5164f4f566eb149c60c83d4896edbf3012943ed%26id%3D/download.torrent
	// get id argument and paste it behind url
	$torrentUrl = "$torrentUrl/download.torrent";
}
else if (strpos($url, "sharetv.org") !== false)
{
	// url in the feed: http://sharetv.org/torrent/103163
	// has to be: http://sharetv.org/get/103163
	$torrentUrl = str_replace("info", "get", $torrentUrl);
	$torrentUrl = str_replace("torrent", "get", $torrentUrl);
}	
else if (strpos($url, "torrentspy.com") !== false)
{
	// make torrentspy download url
	// is in the format of: http://www.torrentspy.com/torrent/485100/South_Park_914_Bloody_Marry
	// Deprecated -- has to be: http://ts.searching.com/download.asp?id=478555
	// http://cache.torrentspy.com/download.asp?id=1745474
	$torrentUrl = str_replace("http://www.torrentspy.com/torrent/", "", $torrentUrl);
	$id = explode("/", $torrentUrl);
	$torrentUrl = "http://cache.torrentspy.com/download.asp?id=$id[0]";
}

else if (strpos($url, "torrentreactor.net") !== false || strpos($url, "Torrentreactor.Net") !== false)
{
	// make torrentreactor download url
	// url in rss: http://torrentreactor.net/view.php?id=5307280
	// has to be: http://dl.torrentreactor.net/download.php?id=645941&name=Prison.Break.S02E09.PROPER.HDTV.XviD-XOR.VOST-FRENCH-PM3
	//String[] tempUrl;
	$tempUrl = explode("id=", $torrentUrl);
	
	// filter [Series TV/Show Showname] from name
	//String[] tempName;
	$tempName = explode("] ", $sTitle);
	$name = urlencode($tempName[1]);
	$torrentUrl = "http://dl.torrentreactor.net/download.php?name=$name&id=$tempUrl[1]";
}
else if (strpos($url, "bushtorrent.com") !== false || strpos($url, "Bushtorrent.com") !== false)
{
	// same as torrentreactor
	
	// make bushtorrent download url
	// url in rss: http://bushtorrent.com/view.php?id=5307280
	// has to be: http://dl.bushtorrent.com/download.php?id=645941&name=Prison.Break.S02E09.PROPER.HDTV.XviD-XOR.VOST-FRENCH-PM3
	//String[] tempUrl;
	$tempUrl = explode("id=", $torrentUrl);
	
	// filter [Series TV/Show Showname] from name
	//String[] tempName;
	$tempName = explode("] ", $sTitle);
	$name = urlencode($tempName[1]);
	$torrentUrl = "http://dl.bushtorrent.com/download.php?name=$name&id=$tempUrl[1]";
}
else if(strpos($url, "isohunt.com") !== false)
{	
	// url in rss = http://isohunt.com/torrent_details/14578531/lost (where lost is the query used to make rss feed)
	// has to be: http://isohunt.com/download/14578531/lost
	$torrentUrl = str_replace("torrent_details", "download", $torrentUrl);
	
	// or url in rss = http://isohunt.com/release/14578531/lost (where lost is the query used to make rss feed)
	// points to release page, not useful
	if (strpos($url, "release") !== false)
	{
		$torrentUrl = "null";
	}
	
}
else if(strpos($url, "mininova") !== false)
{
	// url in rss http://www.mininova.org/tor/239805
	// has to be: http://www.mininova.org/get/239805
	$torrentUrl = str_replace("tor", "get", $torrentUrl);
}
else if(strpos($url, "seedler.org") !== false)
{
	// url in rss: http://www.seedler.org/en/html/info/499236
	// has to be: http://www.seedler.org/download.x?id=499236
	$torrentUrl = str_replace("en/html/info/", "download.x?id=", $torrentUrl);
}
else if(strpos($url, "newtorrents.info") !== false)
{
	// url in rss: http://www.newtorrents.info/?id=6697
	// has to be: http://www.newtorrents.info/down.php?id=6697
	$tempName = explode("] ", $sTitle);
	$name = urlencode($tempName[1]);
	$torrentUrl = str_replace("/?id=", "/down.php?p=$name.torrent", $torrentUrl);
}
else if(strpos($url, "xtvi.com") !== false)
{
	// url in rss: http://www.xtvi.com/index.php?torrent=18990&amp;.torrent
	// has to be: http://www.xtvi.com/main.php?mode=gettorrent&id=18969
	$torrentUrl = str_replace("index.php?torrent=", "main.php?mode=gettorrent&id=", $torrentUrl);	
}
else if (strpos($url, "torrentportal.com") !== false)
{
	// url in rss: http://www.torrentportal.com/details/955139/Law.and.Order.S17E16.HDTV.XviD-NoTV.torrent
	// has to be: http://www.torrentportal.com/download/955139/Law.and.Order.S17E16.HDTV.XviD-NoTV.torrent
	$torrentUrl = str_replace("details", "download", $torrentUrl);
}
else
{
	$torrentUrl = "null";
}
//return torrentUrl;
echo($torrentUrl);



?>