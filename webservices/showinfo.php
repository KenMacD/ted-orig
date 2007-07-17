<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>ted show info</title>
</head>

<body>

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
			
			$tempstatus = strstr ($tvcom_content, "<div class=\"f-bold f-666 f-11\">");
			$endpos = strpos ($tempstatus, "<div class=\"mt-10\">");
			$tempstatus = substr ($tempstatus, 0, $endpos);
			$tempstatus = str_replace("</span>", "<br>", $tempstatus);
			$tempstatus = str_replace("<span class="f-333">", "", $tempstatus);
			
			$tempim = strstr($tvcom_content, "<a class=\"default-image more");
			$endpos = strpos ($tempim, "</a>");
			$tempim = substr ($tempim, 0, $endpos+4);
			//$tempim = str_replace("More Pictures", "", $tempim);
			
			// change \> with >
			$description = str_replace("/>", ">", $description);
			$tempim = str_replace("/>", ">", $tempim);
			
		}
	
	
	
	

	function getPageContent($url)
	{
		//$tempVar = "";
		$page = "";
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
		return $tempVar;
	}

?>
<table border="0" align="left">
<tr valign="top">
	<td align="left" valign="top" bgcolor="#CCCCCC">
		<font color="#000000" size="2" face="Arial, Helvetica, sans-serif">
			<?echo($tempim);?><br>
			<a href="<?echo($tvcom_url)?>">copyright TV.com</a><br>
			<?echo($tempstatus)?>
		</font>
	
	
	</td>
	<td valign="top">
		<font color="#000000" size="3" face="Arial, Helvetica, sans-serif">
			<?echo($description);?>
		</font>
	</td>
</tr>
</table>

</body>
</html>