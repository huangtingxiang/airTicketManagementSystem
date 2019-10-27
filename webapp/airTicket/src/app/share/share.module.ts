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
import {SvgIconComponent} from './component/svg-icon/svg-icon.component';
import {CommonModule} from '@angular/common';
import {PlaneTypePipe} from './pipe/plane-type.pipe';
import {CitySelectComponent} from './component/city-select/city-select.component';
import {ErrorDialogComponent} from './component/error-dialog/error-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';


@NgModule({
  declarations: [SvgIconComponent, PlaneTypePipe, CitySelectComponent, ErrorDialogComponent],
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
    CommonModule,
    MatDialogModule
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
    CitySelectComponent,
    MatDialogModule
  ],
  entryComponents: [ErrorDialogComponent]
})
export class ShareModule {
}
