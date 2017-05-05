local Count = 0

					--producer id
for Index, Value in pairs(redis.call('hgetall', 'person:' .. KEYS[8])) do
  Count = Count + 1
end
if Count == 0 then
	return -1
end

Count = 0
						--actor1 id
for Index, Value in pairs(redis.call('hgetall', 'person:' .. KEYS[9])) do
  Count = Count + 1
end
if Count == 0 then
	return -1
end

Count = 0
						--actor2 id
for Index, Value in pairs(redis.call('hgetall', 'person:' .. KEYS[10])) do
  Count = Count + 1
end
if Count == 0 then
	return -1
end

Count = 0
						--actor3 id
for Index, Value in pairs(redis.call('hgetall', 'person:' .. KEYS[11])) do
  Count = Count + 1
end
if Count == 0 then
	return -1
end

						--film id
for Index, Value in pairs(redis.call('hgetall', 'film:' .. KEYS[12])) do
  Count = Count + 1
end
if Count == 0 then
	Count = redis.call('get', 'counters:films');
else Count = tonumber(KEYS[12])
end

redis.call('hmset', 'film:' .. tostring(Count), 'name', KEYS[1], 'year', KEYS[2],
'duration', KEYS[3], 'country', KEYS[4], 'rating', KEYS[5], 'description', KEYS[7])

redis.call('sadd', 'film_genres:' .. tostring(Count), KEYS[6]);

redis.call('hset', 'films_names', KEYS[1], Count);

redis.call('sadd', 'film_actors:' .. tostring(Count), KEYS[9]);
redis.call('sadd', 'film_actors:' .. tostring(Count), KEYS[10]);
redis.call('sadd', 'film_actors:' .. tostring(Count), KEYS[11]);

redis.call('sadd', 'film_producers:' .. tostring(Count), KEYS[8]);

redis.call('set', 'counters:films', Count + 1);

return Count
