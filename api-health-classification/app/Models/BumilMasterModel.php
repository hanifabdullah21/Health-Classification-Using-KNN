<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Carbon\Carbon;

class BumilMasterModel extends Model{
    protected $table = 'bumil_master';

    protected $guarded = [];

    protected $hidden = [];

    protected $appends = [
        'umur_kehamilan',
        'usia'
    ];

    public function account(){
        return $this->belongsTo('App\Models\Account', 'account_id');
    }

    public function dusun(){
        return $this->belongsTo('App\Models\Dusun', 'dusun_id');
    }

    public function classification() {
        return $this->hasMany('App\Models\BumilClassificationModel', 'bumil_id');
    }

    public function getUmurKehamilanAttribute() {
        $tgl_lahir = new Carbon($this->tanggal_kehamilan);
        return $tgl_lahir->diffInMonths(Carbon::today());
    }

    public function getUmurAttribute() {
        $tgl_lahir = new Carbon($this->tanggal_lahir);
        return $tgl_lahir->diffInMonths(Carbon::today());
    }
}