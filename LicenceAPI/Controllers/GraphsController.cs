using LicenceAPI.Models;
using LicenceAPI.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace LicenceAPI.Controllers
{
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

        [HttpGet("blob")]
        public async Task<ActionResult<string>> GetBlob([FromQuery] Guid id)
        {
            return Ok(await graphsService.GetBlob(await graphsService.GetById(id)));
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
        public async Task<IActionResult> PostGraph([FromBody] string text, [FromQuery] string name)
        {
            await graphsService.Insert(new Graph()
            {
                Name = name
            }, text);

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
