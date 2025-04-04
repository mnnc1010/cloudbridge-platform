 /**
  * Interface representing a resource item.
  *@property id - the unique generated id of file when uploaded.
  * @property fileName - The name of the file.
  * @property fileType - The type of the file.
  * @property fileDescription - the description if file.
  * @property fileStorage - The storage location (e.g., "Mongo", "Dynamo", "S3").
  * @property dateInserted - The ISO date string when the file was inserted.
  * @property dateModified - The ISO date string when the file was last modified.
  */
  export interface Resource {
      id: string;
      fineName: string;
      fileType: string;
      fileDescription: string;
      fileStorage: string;
      dateInserted: string;
      dateModified: string;
      }
