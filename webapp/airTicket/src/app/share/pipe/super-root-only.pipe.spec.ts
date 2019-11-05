import { SuperRootOnlyPipe } from './super-root-only.pipe';

describe('SuperRootOnlyPipe', () => {
  it('create an instance', () => {
    const pipe = new SuperRootOnlyPipe();
    expect(pipe).toBeTruthy();
  });
});
