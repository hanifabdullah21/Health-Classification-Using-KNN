<?php

namespace App\Http\Controllers;

use App\Models\Account;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class Auth extends Controller
{

    public function register(Request $req) 
    {
        $account = new Account;
        $account->username = $req->username;
        $account->email = $req->email;
        $account->password = Hash::make($req->password);
        $account->save();

        return response()->json($account);
    }

    public function login(Request $req) {
        $account = Account::where('username', $req->username)->first();

        if(!$account){
            return response()->json(['message' => 'username tidak ditemukan']);
        }

        if (Hash::check($req->password, $account->password)) {
            return response()->json($account);
        }

        return response()->json(['message' => 'salah goblok']);
    }

}
