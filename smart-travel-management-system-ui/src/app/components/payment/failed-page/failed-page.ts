import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-failed-page',
  imports: [],
  templateUrl: './failed-page.html',
  styleUrl: './failed-page.css',
})
export class FailedPage implements OnInit{

  ngOnInit() {
    console.log("Payment Failed page work..")
  }

}
