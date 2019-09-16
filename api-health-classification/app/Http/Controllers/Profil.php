<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class Profil extends Controller
{
    public function __construct() {
      parent::__construct();
    }

    public function profil(Request $req) {
      return $this->response->success($req->account);
    }

    
}
