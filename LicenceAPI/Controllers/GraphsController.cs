using LicenceAPI.Models;
using LicenceAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LicenceAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class GraphsController : ControllerBase
    {
        private readonly IGraphsService graphsService;

        public GraphsController(IGraphsService graphsService)
        {
            this.graphsService = graphsService;
        }


        [HttpGet]
        public async Task<ActionResult<IEnumerable<Graph>>> GetGraphs()
        {
            return Ok(await graphsService.GetAll());
        }

        [HttpGet("active")]
        public async Task<ActionResult<IEnumerable<EntityDTO>>> GetActiveGraphs()
        {
            var items = (await graphsService.GetAll()).Where(w => !w.Pending)
                                                      .Select(async s => new EntityDTO
                                                      {
                                                          Name = s.Name,
                                                          Text = (await graphsService.GetBlob(s))
                                                      }).
                                                      Select(s => s.Result);

            return Ok(items);
        }

        [HttpGet("blob")]
        public async Task<ActionResult<Blob>> GetBlob([FromQuery] Guid id)
        {
            return Ok(new Blob()
            {
                Text = await graphsService.GetBlob(await graphsService.GetById(id))
            });
        }

        [HttpGet("accept")]
        public async Task<ActionResult> AcceptGraph([FromQuery] Guid id)
        {
            var graph = await graphsService.GetById(id);

            graph.Pending = false;

            await graphsService.Update(graph);

            return Ok();
        }


        [HttpPost]
        public async Task<IActionResult> PostGraph([FromBody] EntityDTO entity)
        {
            var item = (await graphsService.GetAll()).FirstOrDefault(f => f.Name == entity.Name);

            if (item != null)
                return BadRequest();

            await graphsService.Insert(new Graph()
            {
                Name = entity.Name
            }, entity.Text);

            return Ok();
        }

        [HttpDelete]
        public async Task<IActionResult> DeleteGraph([FromQuery] Guid id)
        {
            await graphsService.Delete(id);

            return Ok();
        }
    }
}
