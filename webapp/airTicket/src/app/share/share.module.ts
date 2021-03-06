import {NgModule} from '@angular/core';
import {ChartModule} from 'primeng/chart';
import {
  MatAutocompleteModule,
  MatButtonModule, MatCardModule,
  MatCheckboxModule, MatChipsModule, MatDatepickerModule, MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule, MatIconModule,
  MatInputModule,
  MatListModule, MatMenuModule, MatNativeDateModule, MatPaginatorModule, MatRadioModule, MatSelectModule,
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
import {UploadImageComponent} from './component/upload-image/upload-image.component';
import {AirlineCompanySelectComponent} from './component/airline-company-select/airline-company-select.component';
import {PlaneSeatComponent} from './component/plane-seat/plane-seat.component';
import {SuperRootOnlyPipe} from './pipe/super-root-only.pipe';


@NgModule({
  declarations: [SvgIconComponent, PlaneTypePipe, CitySelectComponent, ErrorDialogComponent,
    UploadImageComponent, AirlineCompanySelectComponent, PlaneSeatComponent, SuperRootOnlyPipe],
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
    MatDialogModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatMenuModule,
    ChartModule
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
    MatDialogModule,
    UploadImageComponent,
    AirlineCompanySelectComponent,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatCardModule,
    MatMenuModule,
    ChartModule,
    SuperRootOnlyPipe
  ],
  entryComponents: [ErrorDialogComponent, PlaneSeatComponent]
})
export class ShareModule {
}
