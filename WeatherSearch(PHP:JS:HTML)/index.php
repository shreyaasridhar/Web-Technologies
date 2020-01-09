<!doctype html>
<html>
<head>
<style type="text/css">
	html,body{
		min-height: auto;
		min-width: auto;
		margin: 0 auto;
		overflow: auto;
	}
	.green-box-container{
		margin: auto;
		position: absolute;
		top: 30px;
		left: 300px;
		text-align: center;
	}
	.form-div{
		position: absolute;
		border-radius: 25px;
		padding-left: 20px;
		height: 230px;
		width: 700px;
		background: #40AC39;
	}
	.left-float{
		text-align: left;
		float: left;
		width: 50%;
		padding-left: 30px;
	}
	.vl {
	  border-left: 4px solid white;
	  border-radius: 20px;
	  height: 110px;
	  float: left;
	}

	.block input, .block select{
		display: inline-block;
		width: 100px;
		margin: 0;
		margin-bottom: 10px;
	}
	.block select{
		width: 160px; 
	 }

	.left-float:after{
		content: "";
		display: block;
		clear: both;
	}
	.error-container{
		left: 300px;
		border: 4px solid grey;
		background-color: lightgrey;
		text-align: center;
	}
	#error{
		padding-left: 450px;
		width: 400px;
	}
	.static-container{
		position: static;
		height: 290px;
		width: 100%;
	}
	.weather-search{
		padding-top: 10px;
		font-style: italic; 
		font-size: 40px;
		text-align: center; 
		color: white;
		margin: 0;
	}
	option{
		padding: 0;
	}
	form label {
		color: white;
		text-align: center;
	}

	.button {
	    /*text-align: center;*/
	    padding-right: 140px;
	    padding-top: 10px;
	    clear: both;
	}

	.response-search{
		position: static;
	}
	.currently{
		padding-left: 400px;
		text-decoration: none;
	}

	.current-div{
		background: #00BFFF;
		color: white;
		height: 350px;
		width: 550px; 
		border-radius: 25px;
		/*padding: 30px 100px; */
	}

	.curdiv-elem{
		padding-left: 20px;
	}

	.city-div{
		padding-top: 17px;
		font-size: 40px;
		font-weight: bold;
	}
	.summary-div{
		font-size: 45px;
		font-weight: bold;
		padding-bottom: 10px;
	}

	.temperature-div{
		font-size: 100px;
		font-weight: bold;
		font-family: "Times New Roman";
		float: left;
	}
	.float_container:before,.float_container:after {
	    content: "";
	    display: table;
    }
	.float_container:after{
		clear: both;
	}

	.current-div img{
		height: 30px;
		width: 30px;
	}
	img{
		height: 15px;
		width: 15px;
	}
	.float-card-elem{
		padding-right: 30px;
	    padding-left: 10px;
	    font-weight: bold;
	    float: left;
	    font-size: 20px;
	}
	.float-img-elem{
		float: left;
	    padding-left: 10px;
	    padding-right: 30px;
	}
	.daily{
		padding-left: 220px;
		padding-top: 30px;
		width: auto;
	}
	.daily-table,.daily-table th,.daily-table td{
		border: 2px solid #1e96c9;
		background-color: #A8C7ED;
		border-collapse: collapse;
		text-align: center;
	}
	.daily-table{
		width: 950px ;
	}
	
	.daily-table td,.daily-table th{
		color: white;
		height: 40px;
		font-weight: bold;
		vertical-align: center;
	}
	#table-submit{
		color: white;
		border:  none;
		background-color: inherit;
		font-size: 17px;
		font-weight: bold;
		font-family: "Times New Roman";
	}
	.detailedweather{
		padding-left: 400px;
	}
	.current-hourly{
		background-color: #A3D0D5;
		height: 430px;
		width: 550px;
		color: white;
		border-radius: 25px;
	}
	.detailed-icon{
		height: 220px;
		width: 220px;
	}
	.detailed-left{
		padding-left: 20px;
		padding-top: 100px;
		/* padding-right: 90px; */
		width: 310px;
		height: 100px;
		float: left;
	}
	.detail-summary{
		font-size: 25px;
		padding-bottom: 10px;
		height: 30px;
		font-weight: bold;
	}
	.detail-temp{
		font-size: 100px;
		font-weight: bold;
	}
	.detailed-right{
		/* width: 100px; */
	}
	.other-detail{
		padding-left: 150px;
		padding-top: 10px;
		text-align: center;
		font-size: 20px;
	}
	.other-detail div{
		/* padding-bottom: 3px; */

	}
	.det-val{
		font-weight: bold;
		font-size: 23px;
		display: inline-block;
	}
	.suntime{
		display: inline-block;
	}
	.dayhourly{
		padding-left: 400px;
	}
	#show, #hide{
		padding-left: 230px;
		height: 50px;
		width: 50px;
	}
	#show{
		display: none;
	}
	#hide{
		display: block;
	}
	#chart_div{
		height: 250px;
		width: 900px;
	}
	.graph{
		padding-left: 230px;
	}
</style>
<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>
<script type="text/javascript">
	is_clear = false;
	function loadJSON(url) {
		xmlhttp = new XMLHttpRequest();
		xmlhttp.open("GET",url,false); 
		xmlhttp.send();
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			jsonObj= JSON.parse(xmlhttp.responseText);
			return jsonObj;
		}    
	}  
	// jsonObj = loadJSON('https://csci571.com/hw/hw6/States.txt');
	jsonObj = JSON.parse('{"States":[{"Abbreviation":"AL","State":"Alabama"},{"Abbreviation":"AK","State":"Alaska"},{"Abbreviation":"AZ","State":"Arizona"},{"Abbreviation":"AR","State":"Arkansas"},{"Abbreviation":"CA","State":"California"},{"Abbreviation":"CO","State":"Colorado"},{"Abbreviation":"CT","State":"Connecticut"},{"Abbreviation":"DE","State":"Delaware"},{"Abbreviation":"DC","State":"District Of Columbia"},{"Abbreviation":"FL","State":"Florida"},{"Abbreviation":"GA","State":"Georgia"},{"Abbreviation":"HI","State":"Hawaii"},{"Abbreviation":"ID","State":"Idaho"},{"Abbreviation":"IL","State":"Illinois"},{"Abbreviation":"IN","State":"Indiana"},{"Abbreviation":"IA","State":"Iowa"},{"Abbreviation":"KS","State":"Kansas"},{"Abbreviation":"KY","State":"Kentucky"},{"Abbreviation":"LA","State":"Louisiana"},{"Abbreviation":"ME","State":"Maine"},{"Abbreviation":"MD","State":"Maryland"},{"Abbreviation":"MA","State":"Massachusetts"},{"Abbreviation":"MI","State":"Michigan"},{"Abbreviation":"MN","State":"Minnesota"},{"Abbreviation":"MS","State":"Mississippi"},{"Abbreviation":"MO","State":"Missouri"},{"Abbreviation":"MT","State":"Montana"},{"Abbreviation":"NE","State":"Nebraska"},{"Abbreviation":"NV","State":"Nevada"},{"Abbreviation":"NH","State":"New Hampshire"},{"Abbreviation":"NJ","State":"New Jersey"},{"Abbreviation":"NM","State":"New Mexico"},{"Abbreviation":"NY","State":"New York"},{"Abbreviation":"NC","State":"North Carolina"},{"Abbreviation":"ND","State":"North Dakota"},{"Abbreviation":"OH","State":"Ohio"},{"Abbreviation":"OK","State":"Oklahoma"},{"Abbreviation":"OR","State":"Oregon"},{"Abbreviation":"PA","State":"Pennsylvania"},{"Abbreviation":"RI","State":"Rhode Island"},{"Abbreviation":"SC","State":"South Carolina"},{"Abbreviation":"SD","State":"South Dakota"},{"Abbreviation":"TN","State":"Tennessee"},{"Abbreviation":"TX","State":"Texas"},{"Abbreviation":"UT","State":"Utah"},{"Abbreviation":"VT","State":"Vermont"},{"Abbreviation":"VA","State":"Virginia"},{"Abbreviation":"WA","State":"Washington"},{"Abbreviation":"WV","State":"West Virginia"},{"Abbreviation":"WI","State":"Wisconsin"},{"Abbreviation":"WY","State":"Wyoming"}]}');
	console.log(jsonObj);
    function validateForm() {
    	// alert(obj);
    	var obj = document.forms["search_params"];
    	console.log(is_clear);
    	if (is_clear){
    		obj.reset();
    		is_clear = false;
    		return;    	
    	}
    	if (document.getElementById("checkbox").checked == true){
    		return true;	
    	}
    	document.getElementById("error").innerHTML = "";
    	fields = ['street','city','state'];
    	// console.log(fields);
    	for(i=0; i<fields.length; i++){
    		if (fields[i] == "state"){
    			if (obj[fields[i]].value == "State"){
    				// alert("incorrect state value");
    				document.getElementById("error").innerHTML = "<div class = 'error-container'>Please check the input address.</div>";
    				return false;
    			}
    		} else if (obj[fields[i]].value == ""){
    			// alert("incorrect input address value");
    			document.getElementById("error").innerHTML = "<div class = 'error-container'>Please check the input address.</div>";
    				return false;
    			// alert(fields[i] + " cannot be empty");
    		}
    	}
    	return true;
    }
    function current_location(obj){
    	fields = ['street','city','state'];
    	for(i=0; i<fields.length;i++){
    		document.getElementsByName(fields[i])[0].disabled = true;
    	}
    	var endpoint = 'http://ip-api.com/json/';
    	xmlhttp = new XMLHttpRequest();
		xmlhttp.open("GET",endpoint,false); 
		xmlhttp.send();
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
	    	var cur = JSON.parse(xmlhttp.responseText);	
	    	document.getElementById("longitude").value = cur['lon'];
	    	document.getElementById("latitude").value = cur['lat'];	
	    	document.getElementById("city_ipapi").value = cur['city'];	
		}
    }
    function clearresponse(){
    	console.log("verifying if called");
    	document.getElementsByClassName('response-search')[0].innerHTML = '';
 	}
    

</script>
</head>
<body>
	<div class="static-container">
	<div class="green-box-container">
		<div class="form-div">

		<div class="weather-search"> Weather Search</div>
		<form name = "search_params" action ="" onsubmit= "return validateForm()" method="post">
			<div class="left-float">
				<div class="block">
					<label> Street&nbsp;</label>
					<input type="text" name="street" value="" />
				</div>	
				<div class="block">
					<label> City &nbsp;&nbsp;</label>
					<input type="text" name="city" value="" />
				</div>
			
				<div class="block">
					<label> State </label>
					<select  id="state" name="state" value="<?php echo isset($_POST['state']) ? $_POST['state'] : ''?>">
						<option selected>State</option>
						<option disabled="true">-----------------</option>
						<script type="text/javascript">
							var state_arr= [];
						    var select_element = document.getElementById("state");
							for(i=0;i<jsonObj.States.length;i++){
						    	var opt = document.createElement('option');
							    opt.value = jsonObj.States[i]["Abbreviation"];
							    opt.innerHTML = jsonObj.States[i]["State"];
							    select_element.appendChild(opt);
							}
						</script>
					</select>
				</div>
			</div>

			<div class="vl"></div>
			<div class="right-lane" >
				<input type="checkbox" name="checkbox" id="checkbox" onchange="return current_location(this)" />
				<input type="hidden" name="latitude" id="latitude"/>
				<input type="hidden" name="longitude" id="longitude"/>
				<input type="hidden" name="city_ipapi" id="city_ipapi"/>
				<label >Current Location:</label>
			</div>
		
			<div class="button">
				<input type="submit" value="search" name="search" />
				<input type=submit id = "clear" value="clear" onclick="is_clear = true;" />
			</div>
		</form>
		</div>
	</div>
	</div>
	<div id="error"></div>
	<div class = "response-search">
<?php 
if (isset($_POST['search'])){

	$checkbox_used = 0;
	if (!empty($_POST['checkbox'])){
		$checkbox_used = 1;
		$lat = $_POST['latitude'];
		$long = $_POST['longitude'];
		$city = $_POST['city_ipapi'];
		echo "<script>
		fields = ['street','city','state'];
    	for(i=0; i<fields.length;i++){
    		document.getElementsByName(fields[i])[0].disabled = true;
    	}
    	document.getElementsByName('checkbox')[0].checked = true;
    	</script>";
	} else {
		$city = $_POST['city'];
		$street = $_POST['street'];
		$state = $_POST['state'];
		echo "<script>document.getElementsByName('city')[0].value = '".$city."';</script>";
		echo "<script>document.getElementsByName('street')[0].value = '".$street."';</script>";
		echo "<script>document.getElementsByName('state')[0].value = '".$state."';</script>";
		$url = "https://maps.googleapis.com/maps/api/geocode/xml?address=[$street,$city,$state]&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4";
		$xml = simplexml_load_file($url);
		if ($xml->status == 'ZERO_RESULTS'){
			echo "<script>document.getElementById('error').innerHTML = '<div class = \"error-container\">Please check the input address. Invalid input.</div>';</script>";
			exit();
		}
		$lat = $xml->result->geometry->location->lat;
		$long = $xml->result->geometry->location->lng;
	}

$api_forecast_url = "https://api.forecast.io/forecast/e1d34d255f7fee875672f104fa3ef317/$lat,$long?exclude=minutely,hourly,alerts,flags";
$api_forecast = json_decode(file_get_contents($api_forecast_url),true);

echo "<div class = 'currently'><div class = 'current-div'>"; 
// $current_location = to get it from the user
echo "<div class = 'curdiv-elem city-div'> $city </div>";
$timezone = $api_forecast['timezone'];
echo "<div class = 'curdiv-elem timezone-div'> $timezone </div>";
$temperature = $api_forecast['currently']['temperature'];
echo "<div class = 'float_container'><div class = 'curdiv-elem temperature-div'> $temperature </div>";
echo "<img class = 'temperature-div' style = 'height:20px; width:20px;' src = 'https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' /><div style = 'font-size:50px;padding-top:45px;font-weight: bold;' class = 'temperature-div'>F</div></div>";
$summary = $api_forecast['currently']['summary'];
echo "<div class = 'curdiv-elem summary-div'> $summary </div>";
// Displaying the images
echo "<table><tr>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png' class = 'curdiv-elem float-img-elem' style = 'padding-left:20px' title = 'Humidity'></td>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png' class = 'float-img-elem' title = 'Pressure'/></td>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' class = 'float-img-elem' title = 'WindSpeed'/></td>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png' class = 'float-img-elem' title = 'Visibility'/></td>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' class = 'float-img-elem' title = 'CloudCover'/></td>";
echo "<td align='center'><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png' class = 'float-img-elem' title = 'Ozone'/></td></tr>";
$humidity = $api_forecast['currently']['humidity'];
echo "<tr><td align='center'><div style = 'padding-left:20px' class = 'curdiv-elem float-card-elem'> $humidity </div></td>";
$pressure = $api_forecast['currently']['pressure'];
echo "<td align='center'><div class = 'float-card-elem '> $pressure </div></td>";
$wind_speed = $api_forecast['currently']['windSpeed'];
echo "<td align='center'><div class = 'float-card-elem'> $wind_speed </div></td>";
$visibility = $api_forecast['currently']['visibility'];
echo "<td align='center'><div class = 'float-card-elem '> $visibility </div></td>";
$cloudcover = $api_forecast['currently']['cloudCover'];
echo "<td align='center'><div class = 'float-card-elem '> $cloudcover </div></td>";
$ozone =$api_forecast['currently']['ozone'];
echo "<td align='center'><div class = 'float-card-elem '> $ozone </div></td></tr></table>";
echo "</div></div>";
$daily = $api_forecast['daily']['data'];
$rows = 7;
function icon_picker($message){
	if ($message == 'clear-day' or $message == 'clear-night'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'rain'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'snow'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'sleet'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'wind'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'fog'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'cloudy'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png"/ style = "height: 30px; width: 30px;">';}
	if ($message == 'partly-cloudy-day' or $message == 'partly-cloudy-night'){
			return '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png"/ style = "height: 30px; width: 30px;">';}
}

echo "<div class='daily'><table class = 'daily-table'>"; 

	echo "<th>Date</th>
		<th>Status</th>
		<th>Summary</th>
		<th>TemperatureHigh</th>
		<th>TemperatureLow</th>
		<th>Wind Speed</th>";

for($r = 0;$r<=$rows;$r++){ 
    echo "<tr>"; 
    	// global $time;
    	$GLOBALS['time'] = $daily[$r]['time'];
    	echo "<td align='center'>".date("Y-m-d", $daily[$r]['time'])."</td>"; 
    	echo "<td align='center'>".icon_picker($daily[$r]['icon'])."</td>";
    	echo "<td align='center'><form method = 'POST' name = 'table-form".$r."' ><input type= 'submit' value = '".$daily[$r]['summary']."' id = 'table-submit' name = \"summ".$r."\" /><input type = 'hidden' name = 'lat_val' value = ".$lat."><input type = 'hidden' name = 'long_val' value = ".$long."><input type = 'hidden' name = 'time' value = ".$time."><input type = 'hidden' name = 'other-paras' value = ".$checkbox_used.">";
    	if (empty($_POST['checkbox'])){
    	echo "<input type = 'hidden' name = 'city' value = '".$city."'><input type = 'hidden' name = 'street' value = '".$street."'><input type = 'hidden' name = 'state' value = '".$state."'>";}
    	echo "</form></td>"; 
    	echo "<td align='center'>".$daily[$r]['temperatureHigh']."</td>"; 
    	echo "<td align='center'>".$daily[$r]['temperatureLow']."</td>"; 
    	echo "<td align='center'>".$daily[$r]['windSpeed']."</td>"; 
    echo "</tr>"; 
} 
echo "</table></div><br/><br/>";
}
function detailed_weather($lat,$long,$time) 
{
	function detailed_icon_picker($message)
	{
		if ($message == 'clear-day' or $message == 'clear-night'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png"/ class = "detailed-icon">';}
		if ($message == 'rain'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png"/ class = "detailed-icon">';}
		if ($message == 'snow'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png"/ class = "detailed-icon">';}
		if ($message == 'sleet'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png"/ class = "detailed-icon">';}
		if ($message == 'wind'){
			return '<img src="https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png"/ class = "detailed-icon">';}
		if ($message == 'fog'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png"/ class = "detailed-icon">';}
		if ($message == 'cloudy'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png"/ class = "detailed-icon">';}
		if ($message == 'partly-cloudy-day' or $message == 'partly-cloudy-night'){
			return '<img src="https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png"/ class = "detailed-icon">';}
	}
	function precep_text($value){
		if ($value <= 0.001) return "N/A";
		if ($value <= 0.015) return "Very Light";
		if ($value <= 0.05) return "Light";
		if ($value <= 0.1) return "Moderate";
		if ($value > 1) return "heavy";
	}
	// echo "testing the time".$time."https://api.darksky.net/forecast/e1d34d255f7fee875672f104fa3ef317/$lat,$long,$time?exclude=minutely";
	// $api_forecast_url = "https://api.forecast.io/forecast/e1d34d255f7fee875672f104fa3ef317/$lat,$long?exclude=minutely,hourly,alerts,flags";

	// $api_forecast = json_decode(file_get_contents($api_forecast_url),true);
	$hourly = json_decode(file_get_contents("https://api.darksky.net/forecast/e1d34d255f7fee875672f104fa3ef317/$lat,$long,$time?exclude=minutely"),true);
	echo "<div class = 'detailedweather'><h1 style = 'padding-left: 100px'>Daily Weather Detail</h1><div class = 'current-hourly'><div class = 'detailed-left'>"; 
	$summaryhourly = $hourly['currently']['summary'];
	echo "<div class = 'curdiv-elem detail-summary'> $summaryhourly </div>";
	$temperature = round($hourly['currently']['temperature']);
	echo "<div class = 'curdiv-elem temperature-div'> $temperature </div>";
	echo "<img class = 'temperature-div' src = 'https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' /><div style = 'font-size:50px;padding-top:45px;font-weight: bold;' class = 'temperature-div'>F</div></div>";
	echo "<div class = 'detailed-right'>".detailed_icon_picker($hourly['currently']['icon'])."</div>"; 
	echo "<div class = 'other-detail'><div> Precipitation : <div class = 'det-val'>".precep_text($hourly['currently']['precipIntensity'])."</div></div>";
	echo "<div> Chance of Rain : <div class = 'det-val'>".($hourly['currently']['precipProbability']*100 )."</div>&percnt;</div>";
	echo "<div> Wind Speed : <div class = 'det-val'>".$hourly['currently']['windSpeed']."</div> mph</div>";
	echo "<div> Humidity : <div class = 'det-val'>".($hourly['currently']['humidity']*100)."</div>&percnt;</div>";
	echo "<div> Visibility : <div class = 'det-val'>".$hourly['currently']['visibility']."</div> mi</div>";
	echo "<div class = 'suntime'> Sunrise / Sunset : <div id = 'sunrise' class = 'suntime'></div> / <div class = 'suntime' id = 'sunset'></div></div></div></div></div>
			<script> 
			var offset = ".$hourly['offset'].";
			var sunset = new Date((".$hourly['daily']['data'][0]['sunsetTime']." * 1000)).toLocaleTimeString('en-US',{timeZone:'".$hourly['timezone']."'});
			var sunrise = new Date((".$hourly['daily']['data'][0]['sunriseTime']." * 1000)).toLocaleTimeString('en-US',{timeZone:'".$hourly['timezone']."'});
			var n = sunrise.search(':');
			var p = sunrise.search(' ');
			document.getElementById('sunrise').innerHTML = \"<div class = 'det-val'>\"+sunrise.slice(0,n) + \"</div>\" + sunrise.slice(p);
			var n = sunset.search(':');
			var p = sunset.search(' ');
			document.getElementById('sunset').innerHTML = '<div class = \"det-val\">' + sunset.slice(0,n) +'</div>' + sunset.slice(p);
			</script>";
	$hour_data = array();
	for($i = 0;$i<24;$i++){
		array_push($hour_data,$hourly['hourly']['data'][$i]['temperature']);
	}
	echo "<div class = 'dayhourly'><h1 style = 'margin:0;padding-left: 100px; padding-top: 20px;'>Day's Hourly Weather</h1>
		<script>
		function drawBasic() {

			var data = new google.visualization.DataTable();
			data.addColumn('number', 'X');
			data.addColumn('number', 'T');
			var js_hour_data =".json_encode($hour_data).";
			var js_arr_to_addrows = [];
			for (var i = 0; i < 24;i++){
				js_arr_to_addrows.push([i,js_hour_data[i]]);
			}
			console.log(js_hour_data);
			data.addRows(js_arr_to_addrows);

			var options = {
			hAxis: {
			  title: 'Time'
			},
			vAxis: {
			  textStyle: {fontSize: '0.00001'}, 
			  title: 'Temperature'
			}
			};

			var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
			chart.draw(data, options);
    	}

		function showelem(obj){
			
			var graph_dis = document.getElementsByClassName('graph')[0];

			google.charts.load('current', {packages: ['corechart', 'line']});
			google.charts.setOnLoadCallback(drawBasic);
			graph_dis.innerHTML = \"<div id='chart_div'>inner html</div>\";
			var replaceimg = document.getElementById('show');
			obj.style.display = 'none';
			replaceimg.style.display = 'block';
			document.documentElement.scrollTop = 0;
		}
		function hidelem(obj){
			var graph_dis = document.getElementsByClassName('graph')[0];
			graph_dis.innerHTML = '';
			var replaceimg = document.getElementById('hide');
			obj.style.display = 'none';
			replaceimg.style.display = 'block';
			document.documentElement.scrollTop = 0;
		}
		</script>";

	echo "<img src='https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png' id = 'show' onclick = 'hidelem(this)'/>";
	echo "<img src='https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png' id = 'hide' onclick = 'showelem(this)'/>";

	echo "</div>";

	echo "<div class='graph'></div>";


}
function calldetail(){
	if ($_POST['other-paras'] != 1){
	echo "<script>document.getElementsByName('city')[0].value = '".$_POST['city']."';</script>";
	echo "<script>document.getElementsByName('street')[0].value = '".$_POST['street']."';</script>";
	echo "<script>document.getElementsByName('state')[0].value = '".$_POST['state']."';</script>";}
	else{
		echo "<script>
		fields = ['street','city','state'];
    	for(i=0; i<fields.length;i++){
    		document.getElementsByName(fields[i])[0].disabled = true;
    	}
    	document.getElementsByName('checkbox')[0].checked = true;
    	</script>";
	}
	detailed_weather($_POST['lat_val'],$_POST['long_val'],$_POST['time']);
}
if (isset($_POST['summ0'])){
	calldetail();
}

if (isset($_POST['summ1'])){
		calldetail();
}
if (isset($_POST['summ2'])){
		calldetail();

}
if (isset($_POST['summ3'])){
	calldetail();

}
if (isset($_POST['summ4'])){
	calldetail();

}
if (isset($_POST['summ5'])){
	calldetail();

}
if (isset($_POST['summ6'])){
	calldetail();

}
if (isset($_POST['summ7'])){
	calldetail();

}
?>	
</div>
</body>
</html>