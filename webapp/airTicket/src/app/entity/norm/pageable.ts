export class Pageable {
  size: number;
  page: number;

  constructor(size: number, page: number) {
    this.page = page;
    this.size = size;
  }

}
