import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-categoria-productos',
  imports: [],
  templateUrl: './categoria-productos.component.html',
  styleUrl: './categoria-productos.component.css'
})
export class CategoriaProductosComponent implements OnInit{

  categoria !: string;

  constructor(private route: ActivatedRoute){}


  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.categoria = params['categoria'];
    })
  }

}
