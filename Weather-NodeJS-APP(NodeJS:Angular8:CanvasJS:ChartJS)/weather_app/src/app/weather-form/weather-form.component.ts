import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { SubmitweatherService } from '../submitweather.service';
import { ApiData } from './api-data';
import { ResultsTabComponent } from './results-tab/results-tab.component';
import { FavoriteComponent } from './favorite/favorite.component';
import { LocalStorage, SessionStorage, LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
// import { MatAutocompleteModule,MatInputModule } from '@angular/material';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material';

@Component({
  selector: 'weather-form',
  templateUrl: './weather-form.component.html',
  styleUrls: ['./weather-form.component.css']
})
export class WeatherFormComponent implements OnInit{

	apidata : ApiData;
	view_var : boolean = false;
	submitted: boolean = false;
	progress: boolean;
	result_displaying : boolean = true;
	favourites_displaying: boolean;
	filtered_list_cities : Observable<string[]>;
	is_clear: boolean;
	formattedMessage:string;
	incorrect_city : boolean = false;
	err_disp : boolean = false;

  	constructor(private fb: FormBuilder,private _subweather: SubmitweatherService) { 
	this.progress = false;
  	}
  	weatherform = this.fb.group({
		Street : ['',Validators.required],
		City : ['',Validators.required],
		State : [null,Validators.required],
		CurLoc : [''],
		lat: [''],
		lon:[''],
		state_curloc: [''],
		city_curloc: ['']
	});
	get street(){
		return this.weatherform.get('Street');
	}
	get city(){
		return this.weatherform.get('City');
	}
	get state(){
		return this.weatherform.get('State');
	}
	get loc(){
		return this.weatherform.get('CurLoc');
	}
	states_USA = this.getStates();
	options = [];
	new_options = [];

	getInputVal(){
		
		let s = this.weatherform.get('City').value;
		// console.log(s);
		this._subweather.search_places(s)
		.subscribe(
	  		response => {console.log('Success!', response);
	  		if (response.predictions.length == 0){
				  console.log("Enter correct city"); //ERROR
				  this.incorrect_city = true;
	  			return;
			  }
			// this.new_options = []
			this.options = []
	  		// console.log(response.predictions[0].description);
	  		for (let i =0;i<response.predictions.length;i++){
	  			let str = response.predictions[i].description.slice(0,response.predictions[i].description.indexOf(","));
	  			this.options.push(str);
	  		}
	  		// console.log(this.new_options);
	  		// this.options = this.new_options;
			  // console.log(this.options);
			  this.filtered_list_cities = this.weatherform.get('City').valueChanges.pipe(
				startWith(''),map(value=>this._filter(value))
				)
	   	});
	}
	getStates(){
		var obj = JSON.parse('{"States":[{"Abbreviation":"AL","State":"Alabama"},{"Abbreviation":"AK","State":"Alaska"},{"Abbreviation":"AZ","State":"Arizona"},{"Abbreviation":"AR","State":"Arkansas"},{"Abbreviation":"CA","State":"California"},{"Abbreviation":"CO","State":"Colorado"},{"Abbreviation":"CT","State":"Connecticut"},{"Abbreviation":"DE","State":"Delaware"},{"Abbreviation":"DC","State":"District Of Columbia"},{"Abbreviation":"FL","State":"Florida"},{"Abbreviation":"GA","State":"Georgia"},{"Abbreviation":"HI","State":"Hawaii"},{"Abbreviation":"ID","State":"Idaho"},{"Abbreviation":"IL","State":"Illinois"},{"Abbreviation":"IN","State":"Indiana"},{"Abbreviation":"IA","State":"Iowa"},{"Abbreviation":"KS","State":"Kansas"},{"Abbreviation":"KY","State":"Kentucky"},{"Abbreviation":"LA","State":"Louisiana"},{"Abbreviation":"ME","State":"Maine"},{"Abbreviation":"MD","State":"Maryland"},{"Abbreviation":"MA","State":"Massachusetts"},{"Abbreviation":"MI","State":"Michigan"},{"Abbreviation":"MN","State":"Minnesota"},{"Abbreviation":"MS","State":"Mississippi"},{"Abbreviation":"MO","State":"Missouri"},{"Abbreviation":"MT","State":"Montana"},{"Abbreviation":"NE","State":"Nebraska"},{"Abbreviation":"NV","State":"Nevada"},{"Abbreviation":"NH","State":"New Hampshire"},{"Abbreviation":"NJ","State":"New Jersey"},{"Abbreviation":"NM","State":"New Mexico"},{"Abbreviation":"NY","State":"New York"},{"Abbreviation":"NC","State":"North Carolina"},{"Abbreviation":"ND","State":"North Dakota"},{"Abbreviation":"OH","State":"Ohio"},{"Abbreviation":"OK","State":"Oklahoma"},{"Abbreviation":"OR","State":"Oregon"},{"Abbreviation":"PA","State":"Pennsylvania"},{"Abbreviation":"RI","State":"Rhode Island"},{"Abbreviation":"SC","State":"South Carolina"},{"Abbreviation":"SD","State":"South Dakota"},{"Abbreviation":"TN","State":"Tennessee"},{"Abbreviation":"TX","State":"Texas"},{"Abbreviation":"UT","State":"Utah"},{"Abbreviation":"VT","State":"Vermont"},{"Abbreviation":"VA","State":"Virginia"},{"Abbreviation":"WA","State":"Washington"},{"Abbreviation":"WV","State":"West Virginia"},{"Abbreviation":"WI","State":"Wisconsin"},{"Abbreviation":"WY","State":"Wyoming"}]}');
		var state_arr = [];
	    for (var item in obj.States){
	      state_arr.push(obj.States[item].State);
	    }
	    return state_arr;
	}
	getLocation(){
		this._subweather.current_location()
		.subscribe(
	  		response => {console.log('Success!', response);
	  		console.log(response);
	  		this.weatherform.controls['lat'].setValue(response['lat']);
	  		this.weatherform.controls['lon'].setValue(response['lon']);
	  		this.weatherform.controls['state_curloc'].setValue(response['regionName']);
	  		this.weatherform.controls['city_curloc'].setValue(response['city']);
	  		console.log(this.weatherform.value);
   	});
	}
	Display_results(){
		// console.log("results....",this.result_displaying);
		this.view_var = !this.view_var;
		// if (this.isResults() != true){
		this.result_displaying = true;
		this.favourites_displaying = false;	
		// this.view_var = true;
		// }
		// console.log("results...after.",this.result_displaying,this.favourites_displaying);
	}
	isResults(){
		if (this.result_displaying == true) return true;
		else return false;
	}
	Favorites(){
		// console.log("favourites");
		this.view_var = !this.view_var;
		// if (this.isFavs() != true){
			// this.view_var = true;
			this.result_displaying = false;	
			this.favourites_displaying = true;
		// }
		// console.log("favss...after.",this.result_displaying);
		
	}
	isFavs(){
		if (this.favourites_displaying == true) return true;
		else return false;
	}
	private _filter(value:string):string[]{
		const filterValue = value.toLowerCase()
		return this.options.filter(option => option.toLowerCase().includes(filterValue));

	}
	ngOnInit() {
		this.result_displaying = true;
		this.favourites_displaying = false;
		console.log(this.isFavs());
		this.onChanges();
	}
	onChanges(): void {
		let stree = this.weatherform.get('Street');
		let cit = this.weatherform.get('City');
		let sta = this.weatherform.get('State');
		// let curlo = this.weatherform.get('CurLoc');
		this.weatherform.get('CurLoc').valueChanges.subscribe(val => {
			if (val == true){
				// this.weatherform.reset();
				// curlo.setValue(true);
				this.formattedMessage = `My name is trueee.`;
				stree.setValidators(null);
				cit.setValidators(null);
				sta.setValidators(null);
				stree.disable();
				cit.disable();
				sta.disable();
			}else{
				// this.weatherform.reset();
				// curlo.setValue(false);
				this.formattedMessage = `False`;
				stree.setValidators([Validators.required]);
				cit.setValidators([Validators.required]);
				sta.setValidators([Validators.required]);
				stree.enable();
				cit.enable();
				sta.enable();
			}
		});
		}
  onSubmit(){
	if (this.incorrect_city == true) {
		this.err_disp = true;
	} else {
		this.progress = true;
		this.submitted = true;
		this.view_var = true;
		this._subweather.current_info(this.weatherform.value)
		.subscribe(
			response => {console.log('Success!', response);
			if (this.weatherform.value['CurLoc']) {this.apidata = new ApiData(this.weatherform.value['city_curloc'],response['darkskyapi'],response['state-seal'],this.weatherform.value['state_curloc']); console.log(this.apidata); }
			else this.apidata = new ApiData(this.weatherform.value['City'],response['darkskyapi'],response['state-seal'],this.weatherform.value['State']);
			this.progress = false;
			this.result_displaying = true;
			this.favourites_displaying = false;
   	});
  }}
}
