export interface Payload {
  args?: unknown;
  method: 'post' | 'get' | 'delete' | 'put' | 'patch';
  value: unknown;
  error?: unknown;
  isBlob?: boolean;
  isRequestDecoded?: boolean;
}
export interface JsonFile {
  $schema: string;
  payloads: Payload[];
}
