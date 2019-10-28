import {Component, Input, OnInit} from '@angular/core';
import {CommonService} from '../../../core/service/common.service';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent implements OnInit {
  @Input()
  label = '请选择图片';

  @Input()
  imageCtrl: FormControl;

  previewUrl: any;

  originUrl: any;

  uploadFile: File;

  isUploadActon = false;

  constructor(private commonService: CommonService) {
  }

  ngOnInit() {
    this.imageCtrl.valueChanges.subscribe((value) => {
      if (value && !this.isUploadActon) {
        this.originUrl = value;
      }
    });
  }

  selectFile(file) {
    const reader = new FileReader();
    this.uploadFile = file.target.files[0];
    reader.readAsDataURL(this.uploadFile);
    reader.onload = () => {
      this.previewUrl = reader.result;
    };
  }

  upload() {
    this.commonService.uploadImage(this.uploadFile)
      .subscribe((path) => {
        this.isUploadActon = true;
        this.imageCtrl.setValue(path);
      });
  }

}
