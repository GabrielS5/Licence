﻿using System;

namespace LicenceAPI.Models
{
    public class Program
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public Guid BlobId { get; set; }
        public bool Pending { get; set; }
    }
}
