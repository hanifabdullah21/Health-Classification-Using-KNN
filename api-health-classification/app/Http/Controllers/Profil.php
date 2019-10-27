<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Validator;

class Profil extends Controller
{
    public function __construct() {
      parent::__construct();
    }

    public function profil(Request $req) {
      return $this->response->success($req->account);
    }

    public function update(Request $req) {
      $validator = Validator::make($req->all(), [
        'username' => 'required|unique:accounts,username,'.$req->account->id,
        'email' => 'required|unique:accounts,email,'.$req->account->id,
        'nama' => 'required|string'
      ]);

      if ($validator->fails()) return $this->response->notValidInput($validator->errors());

      $req->account->update($req->only('email', 'username', 'nama'));
      return $this->response->success('Berhasil di update');
    }
}
