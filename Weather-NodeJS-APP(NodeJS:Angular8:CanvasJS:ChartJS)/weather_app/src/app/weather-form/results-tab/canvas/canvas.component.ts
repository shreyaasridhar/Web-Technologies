import { Component, OnInit,Input, OnChanges } from '@angular/core';
import { ApiData } from '../../api-data';
import * as CanvasJS from './../canvasjs.min';
import { format, compareAsc } from 'date-fns';
import { SubmitweatherService } from '../../../submitweather.service';
import { generate } from 'rxjs';

@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.css']
})
export class CanvasComponent implements OnInit {
	@Input() weeklydata:ApiData;
	dates: any = [];
	temphigh: any = [];
	templow: any = [];
	daily:any;
	date:any;
	render_chart:boolean = false;
	hum:any;
	visi:any;
	pp:any;
	pi:any;
	ws:any;
	getTempval(){
		return this.daily.currently.temperature;
	}
  constructor(private _subweather: SubmitweatherService) {  }
  detailed_icon_picker(message)
	{
		if (message == 'clear-day' || message == 'clear-night'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";}
		if (message == 'rain'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png";}
		if (message == 'snow'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png";}
		if (message == 'sleet'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png";}
		if (message == 'wind'){
			return "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png";}
		if (message == 'fog'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png";}
		if (message == 'cloudy'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png";}
		if (message == 'partly-cloudy-day' || message == 'partly-cloudy-night'){
			return "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";}
	}

  SendAPIrequest(){
  	console.log("clicked,call api");
  	console.log((document.getElementById('index')).innerHTML);
  	let ind_val = (document.getElementById('index')).innerHTML;
  	this._subweather.day_info((<HTMLInputElement>document.getElementById('lat')).value,(<HTMLInputElement>document.getElementById('lon')).value,this.weeklydata.darksky.daily.data[ind_val].time)
	.subscribe(
  		response => {console.log('Success!', response);
		  this.daily = response;
		document.getElementById('tempdiv').innerHTML = Math.round(this.daily.currently.temperature).toString();
		  this.date = format(new Date(response.currently.time*1000), 'dd/MM/yyyy');
		  this.hum = Math.round(this.daily.currently.humidity * 100);
		  this.pp = Math.round(this.daily.currently.precipProbability * 100);
		  this.visi = Math.round(this.daily.currently.visibility * 100) / 100;
		  this.pi = Math.round(this.daily.currently.precipIntensity * 100) / 100;
		  this.ws = Math.round(this.daily.currently.windSpeed * 100) / 100;
   	});
  }
  content_click(e){
  	console.log("clicked content",e.dataPoint.label);
  	document.getElementById('index').innerHTML = (7 - (e.dataPoint.x/10)).toString() ;
  	document.getElementById('openModalButton').click();
  }

  ngOnInit() {
  	console.log(this.weeklydata.darksky.latitude,this.weeklydata.darksky.longitude);
  	
  	for (let i = 0; i < 7; i++) {
  		this.dates.push(format(new Date(this.weeklydata.darksky.daily.data[i].time*1000), 'dd/MM/yyyy'));
  		this.temphigh.push(Math.round(this.weeklydata.darksky.daily.data[i].temperatureHigh));
  		this.templow.push(Math.round(this.weeklydata.darksky.daily.data[i].temperatureLow));
	  }
	  let chart = new CanvasJS.Chart("chartContainer", {
		animationEnabled: true,
		title: {
			text:"Weekly Weather"
		},
		legend:{
			horizontalAlign: "center",
			verticalAlign: "top"
		},
		dataPointWidth: 20,
		axisX: {
		title: "Days"
		},
		axisY: {	
	   		gridThickness: 0,
			includeZero: false,
			title: "Temperature in Fahrenheit",
			interval: 10,
			margin : 50
		}, 
		data: [{
			legendText: "Day wise temperature range",
			type: "rangeBar",
			click: this.content_click,
			color: "#89d0f2",
			showInLegend: true,
			indexLabel: "{y[#index]}",
			toolTipContent: "<b>{label}</b>: {y[0]} to {y[1]}",
			dataPoints: [
				{ x: 10, y:[this.templow[6],this.temphigh[6]], label: this.dates[6] },
				{ x: 20, y:[this.templow[5],this.temphigh[5]], label: this.dates[5] },
				{ x: 30, y:[this.templow[4],this.temphigh[4]], label: this.dates[4] },
				{ x: 40, y:[this.templow[3],this.temphigh[3]], label: this.dates[3] },
				{ x: 50, y:[this.templow[2],this.temphigh[2]], label: this.dates[2] },
				{ x: 60, y:[this.templow[1],this.temphigh[1]], label: this.dates[1] },
				{ x: 70, y:[this.templow[0],this.temphigh[0]], label: this.dates[0] }
			]
		}]
	});	
	chart.render();

  }
}
