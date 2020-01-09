import { Component, OnInit } from '@angular/core';
import { LocalStorage, SessionStorage, LocalStorageService } from 'ngx-webstorage';
import { ApiData } from './../api-data';

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})

export class FavoriteComponent implements OnInit {
	value : ApiData ;
  array_elem :any = [];
  no_data :boolean = false;
  display_fav_res:boolean = false;
  res_api_data: any;
  constructor(private storage: LocalStorageService) {this.display_fav_res = false; }
  delete_item(ind){
    let new_arr = [];
    for (let i=0;i<this.array_elem.length;i++){
			if (i != ind){
				new_arr.push(this.array_elem[i]);
			}
		}
    this.array_elem = new_arr;
    if (this.array_elem.length == 0){
      let heights = window.innerWidth;
      console.log(heights);
      document.getElementById("fav_display").innerHTML = "No Records.";
      document.getElementById("fav_display").style.background = '#fff3cd';
      document.getElementById("fav_display").style.color = '#b39b56';
      document.getElementById("fav_display").style.paddingLeft = 25+'%'; 
      document.getElementById("fav_display").style.paddingRight = 25+'%'; 
      document.getElementById("fav_display").style.marginLeft = 20+'%'; 
    }
    this.storage.clear('FavouriteStorage');
    this.storage.store('FavouriteStorage',this.array_elem);
  }
  clicked_element(ind){
    this.display_fav_res = true;
    this.res_api_data = this.array_elem[ind].apidata;
    console.log(ind,this.array_elem[ind]);
  }
  ngOnInit() {
    console.log("favourites tab");
    this.array_elem = this.storage.retrieve('FavouriteStorage')
    if (this.array_elem){
      if (this.array_elem.length == 0){
        this.no_data = true;
        let heights = window.innerWidth;
        console.log(heights);
        document.getElementById("fav_display").innerHTML = "No Records.";
        document.getElementById("fav_display").style.background = '#fff3cd';
        document.getElementById("fav_display").style.color = '#b39b56';
        document.getElementById("fav_display").style.paddingLeft = 25+'%'; 
        document.getElementById("fav_display").style.paddingRight = 25+'%'; 
        document.getElementById("fav_display").style.marginLeft = 20+'%'; 

      } else{
        this.no_data = false;
      }
      console.log(this.array_elem);
    } else{
      let heights = window.innerWidth;
      console.log(heights);
      document.getElementById("fav_display").innerHTML = "No Records.";
      document.getElementById("fav_display").style.background = '#fff3cd';
      document.getElementById("fav_display").style.color = '#b39b56';
      document.getElementById("fav_display").style.paddingLeft = 25+'%'; 
      document.getElementById("fav_display").style.paddingRight = 25+'%'; 
      document.getElementById("fav_display").style.marginLeft = 20+'%'; 
      console.log("No records");
    }

  	console.log(this.storage);

  }

}
