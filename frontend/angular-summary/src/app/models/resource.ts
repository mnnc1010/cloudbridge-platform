/**
 * Interface representing a resource item.
 *
 * @property fileName - The name of the file.
 * @property fileType - The type of the file.
 * @property fileStorage - The storage location (e.g., "Mongo", "Dynamo", "S3").
 * @property dateInserted - The ISO date string when the file was inserted.
 * @property dateModified - The ISO date string when the file was last modified.
 */
export interface Resource {
  fileName: string;
  fileType: string;
  fileStorage: string;
  dateInserted: string;
  dateModified: string;
}
