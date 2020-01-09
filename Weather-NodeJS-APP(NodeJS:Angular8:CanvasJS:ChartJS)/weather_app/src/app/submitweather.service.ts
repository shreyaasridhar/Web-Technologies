import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SubmitweatherService {

	_url = 'http://localhost:8081/currentweather';
	_ipapi = 'http://ip-api.com/json';
  _url_auto = 'http://localhost:8081/autocomplete';
  _day_api = 'http://localhost:8081/daily';
  constructor(private _http: HttpClient) { }

  current_info(weatherData){
  	return this._http.post<any>(this._url, weatherData);
  }

  current_location(){
  	return this._http.get<any>(this._ipapi);
  }

  search_places(s){
    let data={"city":s};
    console.log(data);
    return this._http.post<any>(this._url_auto,data);
  }
  day_info(la,lo,ti){
    let data = {
      "lat":la,
      "lon":lo,
      "time":ti
    }
    console.log(data);
    return this._http.post<any>(this._day_api,data);
  }

}
