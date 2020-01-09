import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { ApiData } from '../api-data';
import { Label, Color } from 'ng2-charts';
import { LocalStorage, LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-results-tab',
  templateUrl: './results-tab.component.html',
  styleUrls: ['./results-tab.component.css']
})
export class ResultsTabComponent implements OnChanges{
	@Input() Viewresult: ApiData = null;
	@Input() Viewable: boolean;
	@Input() Submitstatus: boolean; 
	fav : any = []
	apiweekly : ApiData = null;
	display_chart: string ;
	activeTab = 'currently';
	temperature : any = [];
	pressure : any = [];
	humidity : any = [];
	ozone : any = [];
	visibility : any = [];
	windspeed : any = [];
	twitter_str : string;
	weather_vars = ["Temperature","Pressure","Humidity","Ozone","Visibility","Wind Speed"];
	lables : any = [];
	urltwitter: string;
	isStar : boolean = false;
	week_active : boolean = false; 
	curtemp(val){
		return Math.round(val);
	}

    public barChartColors : Color[]= [{
    	backgroundColor: '#89d0f2'
	  }];
	  
	public TemperatureOptions;
	public TemperatureLabels ;
	public TemperatureType ;
	public TemperatureLegend ;
	public TemperatureData ;

    public PressureOptions;
	public PressureLabels;
	public PressureType;
	public PressureLegend;
	public PressureData;

    public HumidityOptions;
	public HumidityLabels; 
	public HumidityType; 
	public HumidityLegend;
	public HumidityData;

    public OzoneOptions;
	public OzoneLabels;
	public OzoneType;
	public OzoneLegend;
	public OzoneData;

    public VisibilityOptions;
	public VisibilityLabels;
	public VisibilityType;
	public VisibilityLegend;
	public VisibilityData;

    public WindspeedOptions;
	public WindspeedLabels;
	public WindspeedType;
	public WindspeedLegend;
	public WindspeedData;

  currently(activeTab){
	this.activeTab = activeTab;
  }

  hourly(activeTab){
    this.activeTab = activeTab;
    this.display_chart = "Temperature";
  }
  weekly(activeTab){
	this.week_active = true;
	this.activeTab = activeTab;
  }
  star(){
	console.log("starpresseed");
	let flag = false;
	let array_elem = [];
	let new_arr = [];
	let ret = this.storage.retrieve('FavouriteStorage');
	if (ret){
		array_elem = ret;
		this.storage.clear('FavouriteStorage');
		for (let i=0;i<array_elem.length;i++){
			if (array_elem[i].city == this.Viewresult.city){
				console.log("matcheedddd");
				flag = true;
				this.isStar = false;
			}else{
				new_arr.push(array_elem[i]);
			}
		}
		array_elem = new_arr;
		if (!flag)
			array_elem.push({"image":this.Viewresult.stateseal,"city":this.Viewresult.city,"state":this.Viewresult.state,"apidata":this.Viewresult});
		this.storage.store('FavouriteStorage',array_elem);
	} else{
		array_elem.push({"image":this.Viewresult.stateseal,"city":this.Viewresult.city,"state":this.Viewresult.state,"apidata":this.Viewresult});
		this.storage.store('FavouriteStorage',array_elem);
	}
	if (flag == false){
		this.isStar = true;
	}
	console.log(this.isStar);
    
    
  }
  constructor(private storage:LocalStorageService) { }

  setLabels(){
  	this.lables = [];
  	for(let i=0; i<24;i++){
  		this.lables.push(i);
  	}
  }

  getPressure(api){
  	this.pressure = [];
  	this.PressureLabels = this.lables;
	this.PressureType = 'bar';
	this.PressureLegend = true;
	this.PressureData = [{data: this.pressure, label: 'pressure'}];
  	for (let i=0; i< 24; i++){
  		this.pressure.push(api.darksky.hourly.data[i]['pressure']);
  	}
  	this.PressureOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: 'Millibars'
    	}}]
    }};
  }

  getHumidity(api){
  	this.humidity = [];
	this.HumidityLabels = this.lables;
	this.HumidityType = 'bar';
	this.HumidityLegend = true;
	this.HumidityData = [{data: this.humidity, label: 'humidity'}];
  	for (let i=0; i< 24; i++){
  		this.humidity.push(api.darksky.hourly.data[i]['humidity']*100);  	
  	}
  	this.HumidityOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: '% Humidity'
    	}}]
    }};
  }
  
  getOzone(api){
  	this.ozone = [];
  	this.OzoneLabels = this.lables;
	this.OzoneType = 'bar';
	this.OzoneLegend = true;
	this.OzoneData = [{data: this.ozone, label: 'ozone'}];
  	for (let i=0; i< 24; i++){
  		this.ozone.push(api.darksky.hourly.data[i]['ozone']);
  	}
  	this.OzoneOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: 'Dobson Units'
    	}}]
    }};
  }

  getVisibility(api){
  	this.visibility = [];
	this.VisibilityLabels = this.lables;
	this.VisibilityType = 'bar';
	this.VisibilityLegend = true;
	this.VisibilityData = [{data: this.visibility, label: 'visibility'}];
  	for (let i=0; i< 24; i++){
  		this.visibility.push(api.darksky.hourly.data[i]['visibility']);
  	}
  	this.VisibilityOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: 'Miles'
    	}}]
    }};
  }

  getWindspeed(api){
  	this.windspeed = [];
  	this.WindspeedLabels = this.lables;
	this.WindspeedType = 'bar';
	this.WindspeedLegend = true;
	this.WindspeedData = [{data: this.windspeed, label: 'windspeed'}];
  	for (let i=0; i< 24; i++){
  		this.windspeed.push(api.darksky.hourly.data[i]['windSpeed']);
  	}
  	this.WindspeedOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: 'Miles per hour'
    	}}]
    }};
  }

  getTemperature(api){
  	this.temperature = [];
  	for (let i=0; i< 24; i++){
  		this.temperature.push(api.darksky.hourly.data[i]['temperature']);
  	}
  	this.TemperatureOptions = {
    scaleShowVerticalLines: false,
	responsive: true,
	legend: {
		onClick: (e) => e.stopPropagation()
	},
    scales: {
    	xAxes:[{
    	scaleLabel: {
    		display: true,
    		labelString: 'Time difference from the current hour'
    	}}],
    	yAxes:[{
    		scaleLabel: {
    		display: true,
    		labelString: 'Fahrenheit'
    	}}]
    }};
	this.TemperatureLabels = this.lables;
	this.TemperatureType = 'bar';
	this.TemperatureLegend = true;
	this.TemperatureData = [
		{data: this.temperature, label: 'temperature'}
	];
  }
  
  ngOnChanges() {
	console.log(this.Viewable, this.Submitstatus);
	let ret = this.storage.retrieve('FavouriteStorage');
	this.isStar = false;
	if (ret){
		let arr= ret;
		for (let i=0;i<arr.length;i++){
			if (arr[i].city == this.Viewresult.city){
				console.log("star_selected");
				this.isStar = true;
			}
		}
	}
	this.setLabels();
  	this.getTemperature(this.Viewresult);
  	this.getPressure(this.Viewresult);
  	this.getHumidity(this.Viewresult);
  	this.getOzone(this.Viewresult);
  	this.getVisibility(this.Viewresult);
  	this.getWindspeed(this.Viewresult);
  	this.apiweekly = this.Viewresult;
	this.twitter_str = 'The current temperature at '+ this.Viewresult.city + ' is '+ this.Viewresult.darksky.currently.temperature +'°F. The weather conditions are '+this.Viewresult.darksky.currently.summary+'.';
  	this.urltwitter="https://twitter.com/intent/tweet?text=" + this.twitter_str + "&button_hashtag=CSCI571WeatherSearch";
  }


}
