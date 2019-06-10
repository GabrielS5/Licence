using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LicenceAPI.Context;
using LicenceAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace LicenceAPI.Services
{
    public class GraphsService : IGraphsService
    {
        private AppDbContext context;
        private IDatastoreService datastoreService;

        public GraphsService(AppDbContext context, IDatastoreService datastoreService)
        {
            this.context = context;
            this.datastoreService = datastoreService;
        }

        public async Task<List<Graph>> GetAll()
        {
            return context.Graphs.ToList();
        }

        public Task<string> GetBlob(Graph graph)
        {
            return datastoreService.GetBlob(graph.BlobId);
        }

        public async Task<Graph> GetById(Guid id)
        {
            return await context.Graphs.FirstOrDefaultAsync(f => f.Id == id);
        }

        public async Task Insert(Graph graph, string text)
        {
            Guid blobId = await datastoreService.InsertBlob(text);

            graph.BlobId = blobId;

            await context.AddAsync(graph);
            await context.SaveChangesAsync();
        }

        public async Task Delete(Guid id)
        {
            Graph graph = await GetById(id);

            context.Remove(graph);

            await context.SaveChangesAsync();
        }

        public async Task Update(Graph graph)
        {
            context.Update(graph);
            await context.SaveChangesAsync();
        }
    }
}
