import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';  
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
// import { FormcompComponent } from './formcomp/formcomp.component'; //the old one
import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WeatherFormComponent } from './weather-form/weather-form.component';
import { ResultsTabComponent } from './weather-form/results-tab/results-tab.component';
import { ChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatAutocompleteModule,MatInputModule,MatOptionModule, MatSelectModule, MatIconModule } from '@angular/material';
import { MatGoogleMapsAutocompleteModule } from '@angular-material-extensions/google-maps-autocomplete';
import { AgmCoreModule } from '@agm/core';
import { CanvasComponent } from './weather-form/results-tab/canvas/canvas.component';
import { FavoriteComponent } from './weather-form/favorite/favorite.component';
import { NgxWebstorageModule } from 'ngx-webstorage';


@Injectable({
  providedIn: 'root'
})

@NgModule({
  declarations: [
    AppComponent,
    WeatherFormComponent,
    ResultsTabComponent,
    CanvasComponent,
    FavoriteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    ChartsModule, 
    BrowserAnimationsModule,
    MatAutocompleteModule,
    MatInputModule,
    MatGoogleMapsAutocompleteModule,
    AgmCoreModule.forRoot({apiKey: 'AIzaSyDCsZoJ-fYrj0ziziKnUvNUa4PmpK8WqQY', //AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4'
          libraries: ['places']
        }),
    NgxWebstorageModule.forRoot(),
    MatInputModule,MatOptionModule, MatSelectModule, MatIconModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
