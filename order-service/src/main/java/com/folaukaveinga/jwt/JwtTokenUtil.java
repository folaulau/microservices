package com.folaukaveinga.jwt;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.folaukaveinga.utility.DateTimeUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@SuppressWarnings("unchecked")
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	private Clock clock = DefaultClock.INSTANCE;

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.lifetime.duration}")
	private Long LIFETIME_DURATION;

	@Value("${jwt.issuer}")
	private String ISSUER;

	/**
	 * claims.put("id", user.getId()); claims.put("email", user.getUsername());
	 * claims.put("roles", user.getRoles());
	 * 
	 * @param token
	 * @return
	 */
	
	public JwtPayload getPlayloadByToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		JwtPayload payload = new JwtPayload();
		payload.setEmail(claims.get("email").toString());
		payload.setExp(claims.getExpiration());
		payload.setFirstName(claims.get("firstName").toString());
		payload.setLastName(claims.get("lastName").toString());
		payload.setIat(claims.getIssuedAt());
		payload.setIss(claims.getIssuer());
		payload.setSub(Long.parseLong(claims.getSubject()));
		payload.setNbf(claims.getNotBefore());
		payload.setRoles(claims.get("roles", List.class));
		return payload;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(JwtPayload jwtPayload) {
		return Jwts.builder()
				.setPayload(jwtPayload.toJson())
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = getLifetimeExpiration(createdDate);
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public Boolean validateToken(String token, String uname, Date lastResetDate) {
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		// final Date expiration = getExpirationDateFromToken(token);
		return (username.equals(uname) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, lastResetDate));
	}

	/**
	 * 1 lifetime is 6 hours
	 * @param createdDate
	 * @return Date
	 */
	private Date getLifetimeExpiration(Date createdDate) {
		return new Date(createdDate.getTime() + DateTimeUtil.getHoursInMilliseconds(LIFETIME_DURATION));
	}
}