using System;
using System.Threading.Tasks;

namespace LicenceAPI.Services
{
    public interface IDatastoreService
    {
        Task<string> GetBlob(Guid id);

        Task<Guid> InsertBlob(string text);
    }
}
