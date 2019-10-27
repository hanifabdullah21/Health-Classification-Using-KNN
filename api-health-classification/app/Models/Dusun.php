<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Dusun extends Model {

    protected $table = "dusun";

    protected $guarded = [];

    protected $hidden = ['created_at', 'updated_at'];

    public function balita(){
        return $this->hasMany('App\Models\Balita', 'dusun_id');
    }
}

