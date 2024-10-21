declare module 'piexifjs' {
  export function dump(exifObj: any): string;
  export function insert(exifStr: string, jpegData: string): string;
  export function TimeStamp(date: Date): string;
}
