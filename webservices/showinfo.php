<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>ted show info</title>
<script language="JavaScript" type="text/javascript" src="pphlogger.js"></script>

<noscript><img alt="" src="http://www.rulecam.net/pphlogger/pphlogger.php?id=ted&st=img"></noscript>

<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-66753-2";
urchinTracker();
</script>

</head>

<body bgcolor="#FFFFFF" text="#000000" link="#0000FF">

<?
	$tvcom = $_GET["tvcom"];
	$description = "";
	
	$tvcom_url="http://www.tv.com/show/" . $tvcom . "/summary.html";
	
	$tvcom_content = getPageContent($tvcom_url);
	
	//foreach ($tvcom_content as $regel)
	//{
	// TODO: now we check per regel, have to check the whole file once. from <div> to div, from <a> to </a>
		//echo($regel);
		// find summary
		if ($description == "")
		{			
			$tempdescription = strstr ($tvcom_content, "<div id=\"continue\"");
			$endpos = strpos ($tempdescription, "</div>");
			$tempdescription = substr ($tempdescription, 0, $endpos);
			
			if ($tempdescription != "")
			{
				$tempdescription = strstr ($tempdescription, "fullTextDisplay");
				$pieces = preg_split ('(\', \')', $tempdescription);
				$description = $pieces[2];	
			}
			else
			{
				// get the short summary, needed if there is no "full" summary
				$tempsummary = strstr ($tvcom_content, "<div id=\"summary_fold\" class=\"mt-10\">");
				$tempsummary = str_replace ("<div id=\"summary_fold\" class=\"mt-10\">", "", $tempsummary);
				$endpos = strpos ($tempsummary, "</div>");
				$tempsummary = substr ($tempsummary, 0, $endpos);
				$description = $tempsummary;
			}
			
			$description = str_replace("\'", "'", $description);
			$description = str_replace("<br /><br /><br /><br />", "<br><br>", $description);
			
			$tempstatus = strstr ($tvcom_content, "<div class=\"f-bold f-666 f-11\">");
			$endpos = strpos ($tempstatus, "<div class=\"mt-10\">");
			$tempstatus = substr ($tempstatus, 0, $endpos);
			$tempstatus = str_replace("</span>", "<br>", $tempstatus);
			$tempstatus = str_replace("<span class=\"f-333\">", "<br>", $tempstatus);
			$tempstatus = str_replace("&nbsp;", "", $tempstatus);
			$tempstatus = str_replace("<div>", "", $tempstatus);
			$tempstatus = str_replace("<div class=\"f-bold f-666 f-11\">", "", $tempstatus);
			
			
			$tempim = strstr($tvcom_content, "<a class=\"default-image");
			$endpos = strpos ($tempim, "</a>");
			$tempim = substr ($tempim, 0, $endpos+4);
			$tempim = str_replace("More Pictures", "", $tempim);
			
			// change \> with >
			$description = str_replace("/>", ">", $description);
			$tempim = str_replace("/>", ">", $tempim);
			
		}
	
	
	
	

	function getPageContent($url)
	{
		//$tempVar = "";
		/*$page = "";
		if($url != ""){
			$tempVar = file($url);
			foreach ($tempVar as $regel)
			{
			$page .= $regel;
			}
        return $page;
		}else{
			$tempVar = "No valid url passed!";
		}
		return $tempVar;*/
		$curl = curl_init();

	  // Setup headers - I used the same headers from Firefox version 2.0.0.6
	  // below was split up because php.net said the line was too long. :/
	  $header[0] = "Accept: text/xml,application/xml,application/xhtml+xml,";
	  $header[0] .= "text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	  $header[] = "Cache-Control: max-age=0";
	  $header[] = "Connection: keep-alive";
	  $header[] = "Keep-Alive: 300";
	  $header[] = "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7";
	  $header[] = "Accept-Language: en-us,en;q=0.5";
	  $header[] = "Pragma: "; // browsers keep this blank.
	
	  curl_setopt($curl, CURLOPT_URL, $url);
	  curl_setopt($curl, CURLOPT_USERAGENT, 'Googlebot/2.1 (+http://www.google.com/bot.html)');
	  curl_setopt($curl, CURLOPT_HTTPHEADER, $header);
	  curl_setopt($curl, CURLOPT_REFERER, 'http://www.google.com');
	  curl_setopt($curl, CURLOPT_ENCODING, 'gzip,deflate');
	  curl_setopt($curl, CURLOPT_AUTOREFERER, true);
	  curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	  curl_setopt($curl, CURLOPT_TIMEOUT, 10);
	
	  $html = curl_exec($curl); // execute the curl command
	  curl_close($curl); // close the connection
	
	  return $html; // and finally, return $html
	}

?>
<table border="0" align="left" cellpadding="4">
<tr valign="top">
	<td align="left" valign="top" bgcolor="#E9E9E9">
		<font size="2" face="Arial, Helvetica, sans-serif">
			<?echo($tempim);?><br>
			<?echo($tempstatus)?><br>
			<br>
			<a href="<?echo($tvcom_url)?>">Detailed show summary</a><br><br>
			<a href="<?echo($tvcom_url)?>">Copyright TV.com</a><br><br>
			Used with permission from CNET Networks, Inc., All rights reserved.
	  </font>
	
	
	</td>
	<td valign="top">
		<font size="3" face="Arial, Helvetica, sans-serif">
			<?echo($description);?>
			
		</font>
	</td>
</tr>
</table>

</body>
</html>