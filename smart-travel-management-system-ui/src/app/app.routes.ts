import { Routes } from '@angular/router';
import { SearchTrip } from './components/trip-schedule/search-trip/search-trip';
import { App } from './app';
import { HomeComponent } from './components/home-component/home-component';
import { SelectSeat } from './components/selectSeat/select-seat/select-seat';
import { UserSelectedData } from './components/user-selected-data/user-selected-data';
import { FailedPage } from './components/payment/failed-page/failed-page';
import { SuccessPage } from './components/payment/success-page/success-page';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'searchTrips', component: SearchTrip },
    {path:'selectSeat',component:SelectSeat},
    {path:'backToselectSeat',component:SelectSeat},
    {path:'showUserSelectedData',component:UserSelectedData},
    {path:'payment/successPage', component:SuccessPage},
    {path:'payment/failedPage',component:FailedPage}
];
