using Microsoft.Azure.Storage;
using Microsoft.Azure.Storage.Blob;
using System;
using System.IO;
using System.Threading.Tasks;

namespace LicenceAPI.Services
{
    public class DatastoreService : IDatastoreService
    {
        private CloudStorageAccount storageAccount;
        private CloudBlobContainer cloudBlobContainer;

        public DatastoreService()
        {
            string storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=licencestorageaccount;AccountKey=MZFXTV0Tsl3td5NifLNaxZxuiBF62yDVDTA7nB3h2SCfeiEfRCCNoWuC/4cF5DZqfRoUQ0p74oegFuGM9cVUvw==;EndpointSuffix=core.windows.net";

            CloudStorageAccount.TryParse(storageConnectionString, out storageAccount);
            CloudBlobClient cloudBlobClient = storageAccount.CreateCloudBlobClient();
            cloudBlobContainer = cloudBlobClient.GetContainerReference("licenceblobstorage");
        }

        public async Task<string> GetBlob(Guid id)
        {
            CloudBlockBlob cloudBlockBlob = cloudBlobContainer.GetBlockBlobReference(id.ToString());

            string text;

            using (var memoryStream = new MemoryStream())
            {
                await cloudBlockBlob.DownloadToStreamAsync(memoryStream);
                text = System.Text.Encoding.UTF8.GetString(memoryStream.ToArray());
            }

            return text;
        }

        public async Task<Guid> InsertBlob(string text)
        {
            Guid id = Guid.NewGuid();

            CloudBlockBlob cloudBlockBlob = cloudBlobContainer.GetBlockBlobReference(id.ToString());
            await cloudBlockBlob.UploadTextAsync(text);

            return id;
        }
    }
}
