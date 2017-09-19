-- Script for wrk loadtest com.tp.loadbalancer.app generate. Runs tests for 1000_000 different id on path GIVEN_PATH/route?id=${generated_id]
local ids = {}
local charset = {}

for i = 48,  57 do table.insert(charset, string.char(i)) end
for i = 65,  90 do table.insert(charset, string.char(i)) end
for i = 97, 122 do table.insert(charset, string.char(i)) end
math.randomseed(os.time())

function string.random(length)

    if length > 0 then
        return string.random(length - 1) .. charset[math.random(1, #charset)]
    else
        return ""
    end
end

for i= 1, 1000000 do
    ids[i] = string.random(7)
end

function request()
    wrk.path = "/route?id="..ids[math.random(#ids)]
    return wrk.format()
end