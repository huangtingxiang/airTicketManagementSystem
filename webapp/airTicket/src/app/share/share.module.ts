import {NgModule} from '@angular/core';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCheckboxModule, MatChipsModule,
  MatFormFieldModule,
  MatGridListModule, MatIconModule,
  MatInputModule,
  MatListModule, MatPaginatorModule, MatRadioModule, MatSelectModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { SvgIconComponent } from './component/svg-icon/svg-icon.component';
import {CommonModule} from '@angular/common';
import { PlaneTypePipe } from './pipe/plane-type.pipe';
import { CitySelectComponent } from './component/city-select/city-select.component';


@NgModule({
  declarations: [SvgIconComponent, PlaneTypePipe, CitySelectComponent],
  imports: [
    MatGridListModule,
    MatListModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatRadioModule,
    MatAutocompleteModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatChipsModule,
    CommonModule
  ],
  exports: [
    MatGridListModule,
    MatListModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatRadioModule,
    MatAutocompleteModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatChipsModule,
    SvgIconComponent,
    PlaneTypePipe,
    CitySelectComponent
  ]
})
export class ShareModule {
}
