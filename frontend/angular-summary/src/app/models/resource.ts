/**
 * Interface representing a resource item as defined in the backend.
 *
 * @property id - Unique identifier for the resource.
 * @property fileName - The name of the file.
 * @property fileType - The type of the file.
 * @property fileStorage - The storage location (e.g., "Mongo", "Dynamo", "S3").
 * @property fileDescription - A description of the file.
 * @property fileSize - The size of the file in bytes.
 * @property fileOwner - The owner of the file.
 * @property dateInserted - The date when the file was inserted, as an ISO date string.
 * @property dateModified - The date when the file was last modified, as an ISO date string.
 */
export interface Resource {
  id: string;
  fileName: string;
  fileType: string;
  fileStorage: string;
  fileDescription: string;
  fileSize: number;
  fileOwner: string;
  dateInserted: string;
  dateModified: string;
}
