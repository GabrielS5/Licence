using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LicenceAPI.Services
{
    public interface IProgramsService
    {
        Task Insert(Models.Program program, string text);

        Task<List<Models.Program>> GetAll();

        Task<Models.Program> GetById(Guid id);

        Task<string> GetBlob(Models.Program program);

        Task Delete(Guid id);

        Task Update(Models.Program program);
    }
}
