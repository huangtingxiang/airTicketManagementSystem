import {Component, Input, OnInit} from '@angular/core';
import {CommonService} from '../../../core/service/common.service';
import {FormControl} from '@angular/forms';
import {ErrorDialogComponent} from '../error-dialog/error-dialog.component';
import {MatDialog} from '@angular/material';
import {HttpErrorResponse} from '@angular/common/http';

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

  constructor(private commonService: CommonService,
              private dialog: MatDialog) {
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
        const title = '上传成功';
        const content = '成功上传图片';
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      }, (response: HttpErrorResponse) => {
        const title = '上传失败';
        const content = '服务器错误:' + response.message;
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}
