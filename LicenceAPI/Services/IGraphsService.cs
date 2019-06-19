using LicenceAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LicenceAPI.Services
{
    public interface IGraphsService
    {
        Task Insert(Graph graph, string text);

        Task<List<Graph>> GetAll();

        Task<Graph> GetById(Guid id);

        Task<string> GetBlob(Graph graph);

        Task Delete(Guid id);

        Task Update(Graph graph);
    }
}
